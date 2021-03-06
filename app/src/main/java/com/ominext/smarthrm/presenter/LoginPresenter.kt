package com.ominext.smarthrm.presenter

import com.ominext.smarthrm.http.ApiClient
import com.ominext.smarthrm.http.ApiService
import com.ominext.smarthrm.model.User
import com.ominext.smarthrm.view.ILogin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LoginPresenter(private var iLogin: ILogin) {

    private val disposables = CompositeDisposable()

    fun login(username: String?, imei: String) {
        val apiClient = ApiClient().getClient().create(ApiService::class.java)
        var disposable: Disposable? = null
        disposable = apiClient
            .login(User(username, imei))
            .map { res ->
                return@map res.body()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                // show progess
                iLogin.onLoading()
            }.doFinally {
                //hide progess
                iLogin.onHideLoading()
            }.subscribe({ data ->
                //check api
                iLogin.onLoginSuccess(data?.token)
                disposable?.let {
                    disposables.remove(it)
                }
            }, {
                iLogin.onLoginFail()
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