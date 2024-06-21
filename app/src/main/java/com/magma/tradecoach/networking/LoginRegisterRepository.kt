package com.magma.tradecoach.networking

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magma.tradecoach.ui.segmentMain.MainActivity
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.utilities.Constants
import com.magma.tradecoach.utilities.PrefSingleton
import com.magma.tradecoach.ext.startActivityWithExtras
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class LoginRegisterRepository @Inject constructor() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val singleton = PrefSingleton.instance
    private val reference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")

    suspend fun login(email: String, password: String,c:Context): Result<FirebaseUser> =
        suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        authResult.result.user?.let { user ->
                            singleton.saveString("id", user.uid)
                            cont.resume(Result.success(user))
                        }

                        singleton.saveBool(Constants.LOGGED_KEY, true)
                        c.startActivityWithExtras(MainActivity::class.java, null)
                    } else {
                        Toast.makeText(c, "Login failed: ${authResult.exception?.message}", Toast.LENGTH_LONG).show()

                        cont.resume(Result.failure(Throwable(authResult.exception)))
                    }
                }

            cont.invokeOnCancellation {

            }
        }

suspend fun register(username: String, country: String, email: String, password: String, c: Context): Boolean {
    return try {


        val result = withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }

        if (result.user != null) {
            val uid = result.user?.uid

            val user = UserDataModel(username =username,uid= uid, emailAddress = email,country = country, currency =  100.000)
            uid?.let { reference.child(it).setValue(user) }
            singleton.saveBool(Constants.LOGGED_KEY, true)
            singleton.saveString("id", uid.toString())
            val intent = Intent(c, MainActivity::class.java)
            c.startActivity(intent)

            true
        } else {
            Toast.makeText(c,"Registration Failed",Toast.LENGTH_SHORT).show()
            println(result)
            false
        }
    } catch (e: Exception) {
        println(e)
        Toast.makeText(c,"Registration Failed",Toast.LENGTH_SHORT).show()

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