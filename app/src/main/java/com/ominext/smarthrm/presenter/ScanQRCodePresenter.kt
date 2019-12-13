package com.ominext.smarthrm.presenter

import android.util.Log
import com.ominext.smarthrm.activity.MainActivity
import com.ominext.smarthrm.http.ApiClient
import com.ominext.smarthrm.http.ApiService
import com.ominext.smarthrm.view.ILogin
import com.ominext.smarthrm.view.IScanQRCode
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ScanQRCodePresenter(private var iScanQRCode: IScanQRCode) {

    private val disposables = CompositeDisposable()

    fun pushInfo() {
        val apiClient = ApiClient().getClient().create(ApiService::class.java)
        var disposable: Disposable? = null
        disposable = apiClient.pushinfo("1234")
            .doOnSubscribe {
                // show progess
            }.doFinally {
                //hide progess
            }.subscribe({ data ->
                //check api

                disposable?.let {
                    disposables.remove(it)
                }
            }, {
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