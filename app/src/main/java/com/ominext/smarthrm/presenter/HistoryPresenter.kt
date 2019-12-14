package com.ominext.smarthrm.presenter

import com.ominext.smarthrm.http.ApiClient
import com.ominext.smarthrm.http.ApiService
import com.ominext.smarthrm.view.IHistory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class HistoryPresenter (private val iHistory: IHistory) {

    private val disposables = CompositeDisposable()

    fun getHistoryData() {
        val apiClient = ApiClient().getClient().create(ApiService::class.java)
        var disposable: Disposable? = null
        disposable = apiClient.getHistory()
            .doOnSubscribe {
                // show progess
                iHistory.onLoading()
            }.doFinally {
                //hide progess
                iHistory.onHideLoading()
            }.subscribe({
                //check api
                val datas = it.body()
                datas?.let {
                    iHistory.onSuccess(data = it)
                }
                disposable?.let {
                    disposables.remove(it)
                }
            }, {
                iHistory.onFail(it)
//                Log.e(MainActivity.TAG, Throwable().toString())
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