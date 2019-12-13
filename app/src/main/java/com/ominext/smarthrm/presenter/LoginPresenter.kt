package com.ominext.smarthrm.presenter

import android.util.Log
import com.ominext.smarthrm.activity.MainActivity
import com.ominext.smarthrm.http.ApiClient
import com.ominext.smarthrm.http.ApiService
import com.ominext.smarthrm.view.ILogin
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginPresenter (private var iLogin: ILogin) {

    private val disposables = CompositeDisposable()

    fun login() {
        val apiClient = ApiClient().getClient().create(ApiService::class.java)
        var disposable: Disposable? = null
        disposable = apiClient.login("1234")
            .doOnSubscribe {
                // show progess
                iLogin.onLoading()
            }.doFinally {
                //hide progess
                iLogin.onHideLoading()
            }.subscribe({ data ->
                //check api
                iLogin.onLoginSuccess()
                disposable?.let {
                    disposables.remove(it)
                }
            }, {
                iLogin.onLoginFail()
                Log.e(MainActivity.TAG, Throwable().toString())
                disposable?.let {
                    disposables.remove(it)
                }
            })
        disposables.add(disposable)
    }

    fun onDetach() {
        disposables.clear()
    }
}