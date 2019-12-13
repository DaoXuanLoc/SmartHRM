package com.ominext.smarthrm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import com.ominext.smarthrm.R
import com.ominext.smarthrm.presenter.LoginPresenter
import com.ominext.smarthrm.view.ILogin
import me.dm7.barcodescanner.zxing.ZXingScannerView


class MainActivity : AppCompatActivity(), ILogin {

    private var prisenter: LoginPresenter? = null

    companion object {
        const val TAG = "TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prisenter = LoginPresenter(this)
    }


    override fun onLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onHideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        prisenter?.onDetach()
    }
}
