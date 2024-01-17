package com.magma.tradecoach.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.magma.tradecoach.model.BlogPostModel
import com.magma.tradecoach.model.MarketCoinModel
import com.magma.tradecoach.model.UserDataModel
import com.magma.tradecoach.networking.CoinsRepository
import com.magma.tradecoach.networking.LoginRegisterRepository
import com.magma.tradecoach.utilities.DatabaseProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginRegisterRepository: LoginRegisterRepository,
private val coinsRepository: CoinsRepository,
                                        private val  databaseRepository:DatabaseProvider
): ViewModel(){



    //Login Data
    private var _loginLiveData = MutableLiveData<FirebaseUser?>()
    var loginLiveData = _loginLiveData as LiveData<FirebaseUser?>

    private var _currentUser = MutableLiveData<UserDataModel?>()
    var currentUser = _currentUser as LiveData<UserDataModel?>
    private var _initialCurrencyListLiveData = MutableLiveData<List<MarketCoinModel>>()
    var initialCurrencyListLiveData = _initialCurrencyListLiveData as LiveData<List<MarketCoinModel>>

    //Register Data
    private var registerMutableData = MutableLiveData<Boolean>()
    var registerData = registerMutableData as LiveData<Boolean>
    //Blog Posts
    private var _blogPostsLiveData = MutableLiveData<ArrayList<BlogPostModel>>()
    val blogPostsLiveData = _blogPostsLiveData as LiveData<ArrayList<BlogPostModel>>

    fun login(email: String, password: String,c:Context) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRegisterRepository.login(email, password,c)
                .fold({
                    _loginLiveData.postValue(it)
                }) {
                    _loginLiveData.postValue(null)
                }
        }
    }

    fun register(username:String,country:String,email: String, password: String,c:Context) {
        viewModelScope.launch {
            registerMutableData.postValue(
                loginRegisterRepository.register(username,country,email, password,c)
            )
        }
    }

    fun getCoins() {
        viewModelScope.launch {
            coinsRepository.getResults().fold({ result ->
                _initialCurrencyListLiveData.postValue(result)
            },
                { throwable ->
                    throwable.printStackTrace()
                })
        }
    }
    fun getUserData(){
        viewModelScope.launch {
                _currentUser.postValue(databaseRepository.getUser())
        }
    }
    fun buyCoins(user:UserDataModel
                 ,coin: MarketCoinModel, quantity: Int) {
        viewModelScope.launch {
            databaseRepository.buyCoins(user,coin,quantity)
        }
    }
    fun sellCoins(user:UserDataModel
                  ,coin: MarketCoinModel, quantity: Int) {
        viewModelScope.launch {
            databaseRepository.sellCoins(user,coin,quantity)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createBlogPost(title:String, content:String, author:String){
        viewModelScope.launch {
            databaseRepository.createBlogPost(title,content,author)
        }
    }
    fun getPosts() {
        viewModelScope.launch {
            try {
                val posts = databaseRepository.getPosts()
                _blogPostsLiveData.postValue(posts as ArrayList<BlogPostModel>?)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

}