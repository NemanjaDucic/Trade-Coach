package com.magma.tradecoach.utilities

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.magma.tradecoach.interfaces.SellCoinsCallback
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
    private val gson = Gson()

    fun sendMessage(text: String?,id:String) {
        val chatMessage = ChatMessage(text, id, "", SessionManager.getUsername())
        databaseRef.child("chat").push().setValue(chatMessage)
    }
    fun fetchTopUsersWithCombinedValue(completion: (List<UserWithCombinedValue>?, error: String?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<UserWithCombinedValue>()

                dataSnapshot.children.forEach { userSnapshot ->
                    try {
                        val userData = userSnapshot.getValue(UserDataModel::class.java)

                        userData?.let { user ->
                            val currency = user.currency ?: 0.0
                            val coinsValue = user.coins?.values?.sumByDouble { it.name?.currentPrice!!.times(it.quantity!!)  } ?: 0.0
                            val combinedValue = currency + coinsValue

                            users.add(UserWithCombinedValue(user.username ?: "", combinedValue))
                        } ?: Log.e("FirebaseError", "userData is null: ${userSnapshot.value}")
                    } catch (e: Exception) {
                        // Log the exception and the raw data that caused the issue
                        Log.e("FirebaseError", "Error creating UserDataModel: ${userSnapshot.value}", e)
                    }
                }

                val sortedUsers = users.sortedByDescending { it.combinedValue }

                val topUsers = sortedUsers.take(3)

                // Log top users
                topUsers.forEachIndexed { index, user ->
                    Log.i("TopUsers", "Top ${index + 1} - Username: ${user.username}, Combined Value: ${user.combinedValue}")
                }

                completion(topUsers, null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Call completion with error message
                completion(null, "Error: ${databaseError.message}")
            }
        })
    }
    fun updateAddsWatched() {

        val addsWatchedRef = databaseRef.child("users").child(SessionManager.getId()).child("addsWatched")

        addsWatchedRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                // Retrieve the current value of addsWatched
                val currentCount = mutableData.getValue(Int::class.java)

                // If the value does not exist, initialize it to 1, else increment by 1
                if (currentCount == null) {
                    mutableData.value = 1
                } else {
                    mutableData.value = currentCount + 1
                }

                return Transaction.success(mutableData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                // Handle completion
                if (error != null) {
                    println("Transaction failed: ${error.message}")
                } else if (committed) {
                    println("Transaction successful!")
                }
            }
        })
    }

    fun updateTransactiondByUser(){
       val transRef = databaseRef.child("users").child(SessionManager.getId()).child("transactionsCompleted")
        transRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                // Retrieve the current value of addsWatched
                val currentCount = mutableData.getValue(Int::class.java)

                // If the value does not exist, initialize it to 1, else increment by 1
                if (currentCount == null) {
                    mutableData.value = 1
                } else {
                    mutableData.value = currentCount + 1
                }

                return Transaction.success(mutableData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                // Handle completion
                if (error != null) {
                    println("Transaction failed: ${error.message}")
                } else if (committed) {
                    println("Transaction successful!")
                }
            }
        })
    }

    suspend fun getUser(): UserDataModel {
        return getUserAsync().await()
    }
    fun fetchTopUsers(completion: (List<UserDataModel>?, error: String?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<UserDataModel>()

                dataSnapshot.children.forEach { userSnapshot ->
                    try {
                        val user = userSnapshot.getValue(UserDataModel::class.java)
                        user?.let { users.add(it) }
                    } catch (e: Exception) {
                        // Log the exception and the raw data that caused the issue
                        Log.e("FirebaseError", "Error deserializing user: ${userSnapshot.value}", e)
                    }
                }

                // Sort users by currency in descending order
                val sortedUsers = users.sortedByDescending { it.currency }

                // Get top 3 users
                val topUsers = sortedUsers.take(3)

                // Call completion with top users
                completion(topUsers, null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Call completion with error message
                completion(null, "Error: ${databaseError.message}")
            }
        })
    }
    fun fetchAllUsers(completion: (List<UserDataModel>?, error: String?) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("users")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<UserDataModel>()

                dataSnapshot.children.forEach { userSnapshot ->
                    try {
                        val user = userSnapshot.getValue(UserDataModel::class.java)
                        user?.let { users.add(it) }
                    } catch (e: Exception) {
                        // Log the exception and the raw data that caused the issue
                        Log.e("FirebaseError", "Error deserializing user: ${userSnapshot.value}", e)
                    }
                }

                completion(users, null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Call completion with error message
                completion(null, "Error: ${databaseError.message}")
            }
        })
    }

    private fun getUserAsync(): Deferred<UserDataModel> {
        val deferred = CompletableDeferred<UserDataModel>()
        databaseRef.child("users").child(SessionManager.getId()).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userDataModel = dataSnapshot.getValue(UserDataModel::class.java)
//                var premiumStatus = (dataSnapshot.child("isPremium"))
                if (userDataModel != null) {
                    println(userDataModel)
                    deferred.complete(userDataModel)
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


    fun buyCoins(user: UserDataModel, coin: MarketCoinModel, quantity: Int): Boolean {



        println(user?.isPremium.toString() + "ja sam debil")
        val totalCost = coin.currentPrice * quantity
        return if (user.currency!! >= totalCost) {
            user.currency = user.currency?.minus(totalCost)

            val userReference = FirebaseDatabase.getInstance().getReference("users").child(user.uid!!)
            val coinsReference = userReference.child("coins")

            val coinId = coin.id

            coinsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val existingCoins = dataSnapshot.children.mapNotNull { it.getValue(CoinModel::class.java) }

                    val existingCoin = existingCoins.find { it.id == coinId }

                    if (existingCoin != null) {
                        // Increase quantity of existing coin
                        println(existingCoin)

                        val newQuantity = existingCoin.quantity?.plus(quantity)
                        coinsReference.child(existingCoin.id!!).child("quantity").setValue(newQuantity)
                    } else {
                        // Add new coin
                        val newIndex = existingCoins.size
                        val newCoin = CoinModel(coinId, coin,quantity,coin.symbol)
                        coinsReference.child(coinId).setValue(newCoin)
                    }
                    user.currency?.let { updateUserFunds(user.uid!!, it) }
                    println("Successfully bought $quantity ${coin.name}(s) for $totalCost. Remaining balance: ${user.currency}")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Failed to read user coins: ${databaseError.toException()}")
                }
            })

            true
        } else {
            println("Insufficient funds to buy $quantity ${coin.name}(s).")
            false
        }
    }

    fun updateUserFunds(id:String,money:Double){
        databaseRef.child("users").child(id).child(
                "currency").setValue(money)
    }

    fun rewardMagmaCoins(points:Double) {
        databaseRef.child("users").child(SessionManager.getId()).child("currency").get().addOnSuccessListener { dataSnapshot ->
            val value = dataSnapshot.getValue(Double::class.java)
            if (value != null) {
                // Increment the value by 1
                val updatedValue = value + points
                // Update the value in the database
                databaseRef.child("users").child(SessionManager.getId()).child("currency").setValue(updatedValue)
                    .addOnSuccessListener {
                        println("Value incremented successfully.")
                    }
                    .addOnFailureListener { e ->
                        println("Failed to increment value: $e")
                    }
            } else {
                println("Value is null.")
            }
        }.addOnFailureListener { e ->
            println("Failed to read value: $e")
        }
    }

    fun sellCoins(user: UserDataModel, coin: MarketCoinModel, quantity: Int, callback: SellCoinsCallback) {
        val userReference = FirebaseDatabase.getInstance().getReference("users").child(user.uid!!)
        val coinsReference = userReference.child("coins")

        val coinId = coin.id

        coinsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val existingCoins = dataSnapshot.children.mapNotNull { it.getValue(CoinModel::class.java) }

                val existingCoin = existingCoins.find { it.id == coinId }

                if (existingCoin != null && existingCoin.quantity!! >= quantity) {
                    // Calculate total earning from selling coins
                    val totalEarning = coin.currentPrice * quantity

                    // Add earnings to user's currency
                    user.currency = user.currency?.plus(totalEarning)

                    // Update user's currency in database
                    user.currency?.let { updateUserFunds(user.uid!!, it) }

                    // Decrease quantity of existing coin
                    val newQuantity = existingCoin.quantity!!.minus(quantity)
                    coinsReference.child(existingCoin.id!!).child("quantity").setValue(newQuantity)

                    callback.onSellSuccess(totalEarning)
                } else {
                    callback.onSellFailure("Insufficient quantity of ${coin.name}(s) to sell.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback.onSellFailure("Failed to read user coins: ${databaseError.toException()}")
            }
        })
    }

//    }

//    fun tradeCoins(
//        user: UserDataModel,
//        sourceCoin: MarketCoinModel,
//        sourceQuantity: Int,
//        targetCoin: MarketCoinModel,
//        targetQuantity: Int
//    ): Boolean {
//        val sourceTotalCost = sourceCoin.currentPrice * sourceQuantity
//
//        if (user.coins?.count { it.value.name == sourceCoin.name } ?: 0 < sourceQuantity) {
//            println("Insufficient ${sourceCoin.name}(s) to trade.")
//            return false
//        }
//
//        val targetTotalCost = targetCoin.currentPrice * targetQuantity
//
//        if (user.currency!! < targetTotalCost) {
//            println("Insufficient funds to receive $targetQuantity ${targetCoin.name}(s).")
//            return false
//        }


//        val sellSuccess = sellCoins(user, sourceCoin, sourceQuantity)

//        if (sellSuccess) {
//            val buySuccess = buyCoins(user, targetCoin, targetQuantity)
//
//            if (buySuccess) {
//                println("Successfully traded $sourceQuantity ${sourceCoin.name}(s) for $targetQuantity ${targetCoin.name}(s).")
//                return true
//            } else {
//                buyCoins(user, sourceCoin, sourceQuantity)
//                println("Failed to complete the trade. Reverted the transaction.")
//            }
//        }

//        return false
//    }
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
    fun increaseCurrencyForUser(amount: Int,callback:(Boolean)-> Unit) {

        databaseRef.child("users").child(SessionManager.getId()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentCurrency = snapshot.child("currency").getValue(Int::class.java) ?: 0

                val newCurrency = currentCurrency + amount

                databaseRef.child("users").child(SessionManager.getId()).child("currency").setValue(newCurrency)
                    .addOnSuccessListener {
                        callback.invoke(true)
                    }
                    .addOnFailureListener { e ->
                        callback.invoke(false)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Database error: ${error.message}")
            }
        })
    }
}