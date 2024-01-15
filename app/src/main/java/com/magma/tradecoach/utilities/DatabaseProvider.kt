package com.magma.tradecoach.utilities

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.magma.tradecoach.model.ChatMessage
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.model.UserDataModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class DatabaseProvider @Inject constructor() {
    private val databaseRef =  FirebaseDatabase.getInstance().reference
    fun sendMessage(text: String?,id:String) {
        val chatMessage = ChatMessage(text, id, "", SessionManager.getUsername())
        databaseRef.child("chat").push().setValue(chatMessage)
    }
    suspend fun getUser(): UserDataModel {
        return getUserAsync().await()
    }
    private fun getUserAsync(): Deferred<UserDataModel> {
        val deferred = CompletableDeferred<UserDataModel>()
        databaseRef.child("users").child(SessionManager.getId()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userDataModel = dataSnapshot.getValue(UserDataModel::class.java)
                if (userDataModel != null) {
                    deferred.complete(userDataModel)
                    println(userDataModel)
                } else {
                    deferred.completeExceptionally(Exception("Failed to retrieve user data"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                deferred.completeExceptionally(databaseError.toException())
            }
        })

        return deferred
    }


    fun buyCoins(user: UserDataModel,coin:MarketCoinModel, quantity: Int): Boolean {
        val totalCost = coin.current_price * quantity
        return if (user.currency!! >= totalCost) {
            user.currency = user.currency?.minus(totalCost)
            println("Successfully bought $quantity ${coin.name}(s) for $totalCost . Remaining balance: ${user.currency}")
            true
        } else {
            println("Insufficient funds to buy $quantity ${coin.name}(s).")
            false
        }
    }
    fun sellCoins(user: UserDataModel, coin: MarketCoinModel, quantity: Int): Boolean {
        val totalEarnings = coin.current_price * quantity
        val coinQuantity = user.coins?.count { it.name == coin.name } ?: 0

        return if (coinQuantity >= quantity) {
            user.currency = (user.currency ?: 0.0) + totalEarnings
            user.coins?.let {
                for (i in 1..quantity) {
                    val coinIndex = it.indexOfFirst { it.name == coin.name }
                    if (coinIndex != -1) {
                        it.removeAt(coinIndex)
                    }
                }
            }

            println("Successfully sold $quantity ${coin.name}(s) for $totalEarnings. New balance: ${user.currency}")
            true
        } else {
            println("Insufficient ${coin.name}(s) to sell.")
            false
        }
    }

}