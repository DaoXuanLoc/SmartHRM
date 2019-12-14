package com.ominext.smarthrm.view

interface ILogin {

    fun onLoading()

    fun onHideLoading()

    fun onLoginSuccess(token: String?)

    fun onLoginFail()
}