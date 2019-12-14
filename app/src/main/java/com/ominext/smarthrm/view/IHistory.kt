package com.ominext.smarthrm.view

import com.ominext.smarthrm.model.History

interface IHistory {

    fun onLoading()

    fun onHideLoading()

    fun onSuccess(data: ArrayList<History>)

    fun onFail(err: Throwable)
}