package com.ominext.smarthrm.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import com.ominext.smarthrm.R
import com.ominext.smarthrm.model.ScanQR
import com.ominext.smarthrm.presenter.ScanQRCodePresenter
import com.ominext.smarthrm.view.IScanQRCode
import kotlinx.android.synthetic.main.activity_scan_qrcode.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*

class ScanQRCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, IScanQRCode {

    companion object {
        const val MY_PERMISSIONS_REQUEST_CAMERA = 100
    }

    lateinit var scannerView: ZXingScannerView

    private var presenter: ScanQRCodePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qrcode)

        scannerView = findViewById(R.id.scanner_view)
//        scannerView.setAspectTolerance(0.5f)
        // Set the scanner view as the content view
        requestPermissionsCamera()
        handleClick()
    }

    private fun handleClick() {
        img_toggle_flash.setOnClickListener {
            scannerView.toggleFlash()
        }
        turn_in.setOnClickListener {
        }

        img_Back.setOnClickListener {
            finish()
        }

        img_history.setOnClickListener {
            showHistory()
        }
    }

    private fun showHistory() {

    }


    private fun requestPermissionsCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    MY_PERMISSIONS_REQUEST_CAMERA
                )

                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            scannerView.setResultHandler(this)
            // Start camera on resume
            scannerView.startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    scannerView.setResultHandler(this)
                    // Start camera on resume
                    scannerView.startCamera()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    override fun onResume() {
        scannerView.startCamera()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val imei = sharedPref.getString("KEY_IMEI", "")
        val token = sharedPref.getString("KEY_TOKEN", "")
        val email = sharedPref.getString("KEY_EMAIL", "")

        presenter?.pushInfo(ScanQR(email, token, imei, rawResult?.text))

        val d = Date(rawResult?.timestamp ?: 100 * 1000)
        Handler().postDelayed({
            scannerView.startCamera()
        }, 200)
    }

    override fun pushInfoSusscess() {
        showDiaLogSusscess()
    }

    private fun showDiaLogSusscess() {
        AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage("Check in success ")
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, _ ->
                    goToHistory()
                })
            .setPositiveButton(
                android.R.string.no,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun goToHistory() {
        val intent = Intent()
        intent.setClass(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    override fun pushInfoFail() {
        showDiaLogFail()
    }

    private fun showDiaLogFail() {
        AlertDialog.Builder(this)
            .setTitle("Fail Scan QR")
            .setMessage("Vui lòng thử lại")
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
