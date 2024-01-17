package com.magma.tradecoach.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.magma.tradecoach.model.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
            if (user.coins != null) {
                for (i in 1..quantity) {
                    user.coins.add(CoinModel(coin.id, coin.symbol, coin.name))
                }
            }
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
    fun tradeCoins(
        user: UserDataModel,
        sourceCoin: MarketCoinModel,
        sourceQuantity: Int,
        targetCoin: MarketCoinModel,
        targetQuantity: Int
    ): Boolean {
        val sourceTotalCost = sourceCoin.current_price * sourceQuantity

        if (user.coins?.count { it.name == sourceCoin.name } ?: 0 < sourceQuantity) {
            println("Insufficient ${sourceCoin.name}(s) to trade.")
            return false
        }

        val targetTotalCost = targetCoin.current_price * targetQuantity

        if (user.currency!! < targetTotalCost) {
            println("Insufficient funds to receive $targetQuantity ${targetCoin.name}(s).")
            return false
        }


        val sellSuccess = sellCoins(user, sourceCoin, sourceQuantity)

        if (sellSuccess) {
            val buySuccess = buyCoins(user, targetCoin, targetQuantity)

            if (buySuccess) {
                println("Successfully traded $sourceQuantity ${sourceCoin.name}(s) for $targetQuantity ${targetCoin.name}(s).")
                return true
            } else {
                buyCoins(user, sourceCoin, sourceQuantity)
                println("Failed to complete the trade. Reverted the transaction.")
            }
        }

        return false
    }
    fun generateRandomUUID(): String {
        val randomUUID = UUID.randomUUID()
        return randomUUID.toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createBlogPost(title: String, content: String, author: String) {
        val randomUUID = UUID.randomUUID().toString()
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val currentTimeFormatted = currentDateTime.format(formatter)
        val newPost = BlogPostModel(author, title, content, randomUUID, currentTimeFormatted)
        val newPostRef = databaseRef.child("blogPosts").child(randomUUID)
        newPostRef.setValue(newPost)
        databaseRef.child("blogPosts")
            .orderByChild("date")
            .limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.childrenCount > Constants.BLOG_POST_LIMIT) {
                        val oldestPostSnapshot = dataSnapshot.children.first()
                        val oldestPostId = oldestPostSnapshot.key
                        databaseRef.child("blogPosts").child(oldestPostId!!).removeValue()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }

    suspend fun getPosts(): List<BlogPostModel> = suspendCoroutine { continuation ->
        val postList = ArrayList<BlogPostModel>()

        val eventListener = object : ValueEventListener {
            var hasResumed = false

            override fun onDataChange(snapshot: DataSnapshot) {
                if (!hasResumed) {
                    postList.clear()
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(BlogPostModel::class.java)
                        if (post != null) {
                            postList.add(post)
                        }
                    }
                    hasResumed = true
                    continuation.resume(postList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (!hasResumed) {
                    hasResumed = true
                    continuation.resumeWithException(error.toException())
                }
            }
        }
        databaseRef.child("blogPosts").addValueEventListener(eventListener)
    }

}