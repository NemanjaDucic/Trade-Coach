package com.magma.tradecoach.networking

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.tradecoach.ui.segmentMain.MainActivity
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.utilities.Constants
import com.magma.tradecoach.utilities.PrefSingleton
import com.magma.tradecoach.utilities.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class LoginRegisterRepository @Inject constructor() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val singleton = PrefSingleton
    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")

    suspend fun login(email: String, password: String,c:Context): Result<FirebaseUser> =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.user?.let { it1 -> singleton.instance.saveString("id", it1.uid) }
                        cont.resume(Result.success(auth.currentUser!!))
                        singleton.instance?.saveBool(Constants.LOGGED_KEY,true)
                        val intent = Intent(c, MainActivity::class.java)
                        c.startActivity(intent)

                    } else {
                        cont.resume(Result.failure(Throwable(it.exception)))
                    }
                }

            cont.invokeOnCancellation {

            }
        }



suspend fun register(username: String, country: String, email: String, password: String, c: Context): Boolean {
    return try {
        val auth = FirebaseAuth.getInstance()

        val result = withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }

        if (result.user != null) {
            val uid = result.user?.uid
            println(uid)
            val user = UserDataModel(username, uid, email, country, 100.0)
            uid?.let { reference.child(it).setValue(user) }
            singleton.instance?.saveBool(Constants.LOGGED_KEY, true)
            val intent = Intent(c, MainActivity::class.java)
            c.startActivity(intent)

            true
        } else {
            Utils.displayToast("Registration Failed")
            println(result)
            false
        }
    } catch (e: Exception) {
        println(e)
        Utils.displayToast("Registration Failed")
        false
    }
}

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun logout() {
        auth.signOut()
    }

    fun deleteAccount() {
        auth.currentUser?.delete()
    }

    fun getUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }
}