package com.ominext.smarthrm.presenter

import com.ominext.smarthrm.http.ApiClient
import com.ominext.smarthrm.http.ApiService
import com.ominext.smarthrm.model.ScanQR
import com.ominext.smarthrm.view.IScanQRCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ScanQRCodePresenter(private var iScanQRCode: IScanQRCode) {

    private val disposables = CompositeDisposable()

    fun pushInfo(scanQR: ScanQR) {
        val apiClient = ApiClient().getClient().create(ApiService::class.java)
        var disposable: Disposable? = null
        disposable = apiClient.pushinfo(scanQR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                // show progess
                println("ScanQRCodePresenter.pushInfo doOnSubscribe")
            }.doFinally {
                println("ScanQRCodePresenter.pushInfo doFinally")
                //hide progess
            }.subscribe({ data ->
                //check api
                println("ScanQRCodePresenter.pushInfo subscribe")
                println("data = [${data}]")
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