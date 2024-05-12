package com.magma.tradecoach.di

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.magma.tradecoach.model.UserDataModel

fun <T : Any> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}
fun LifecycleOwner.observeUserData(liveData: LiveData<UserDataModel?>, body: (UserDataModel?) -> Unit) {
    liveData.observe(this, Observer(body))
}
