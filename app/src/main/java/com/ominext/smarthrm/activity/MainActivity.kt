package com.ominext.smarthrm.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ominext.smarthrm.R
import com.ominext.smarthrm.presenter.LoginPresenter
import com.ominext.smarthrm.view.ILogin
import android.util.Log
import androidx.core.app.ActivityCompat

class MainActvity : AppCompatActivity(), ILogin {

    lateinit var edtEmail: EditText
    lateinit var btLogin: Button

    private var prisenter: LoginPresenter? = null
    var imei: String = ""

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0
        const val TAG = "TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prisenter = LoginPresenter(this)
        prisenter!!.login()
        initView()
    }

    private fun initView() {
        getUniqueIMEIId(this)
        edtEmail = findViewById(R.id.edt_email)

        btLogin = findViewById(R.id.btn_login)

        onclickLogin()
    }

    private fun onclickLogin() {
        btLogin.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, ScanQRCodeActivity::class.java)
            startActivity(intent)
        }
//        prisenter?.login(edtEmail.text.toString())
    }

    private fun getUniqueIMEIId(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestReadPhoneStatePermission()
            }
            imei = telephonyManager.deviceId
            Log.e("imei", "=$imei")
            return if (imei != null && !imei.isEmpty()) {
                imei
            } else {
                Build.SERIAL
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "not_found"
    }

    private fun requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_PHONE_STATE
            )
        ) {
            AlertDialog.Builder(this)
                .setTitle("Permission Request")
                .setMessage(getString(R.string.permissions_not_granted_read_phone_state))
                .setCancelable(false)
                .setPositiveButton(
                    android.R.string.yes
                ) { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
                    )
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initView()
            } else {
                alertAlert(getString(R.string.permissions_not_granted_read_phone_state))
            }
        }
    }

    private fun alertAlert(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Permission Request")
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onLoading() {
    }

    override fun onHideLoading() {
    }

    override fun onLoginSuccess() {
        val intent = Intent()
        intent.setClass(this, ScanQRCodeActivity::class.java)
    }

    override fun onLoginFail(err: Throwable) {
        println(err)
        Toast.makeText(this, "$err", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        prisenter?.onDetach()
    }
}
