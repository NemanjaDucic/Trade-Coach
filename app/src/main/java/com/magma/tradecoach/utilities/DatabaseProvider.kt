package com.magma.tradecoach.utilities

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.magma.tradecoach.model.ChatMessage
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
}