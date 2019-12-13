package com.ominext.smarthrm.diolog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.ominext.smarthrm.R


class AlertCommonDialog(
    context: Context?,
    private val type: Int,
    private var clickYesListener: ((dialog: Dialog) -> Unit)? = null,
    private var clickCancelListener: ((dialog: Dialog) -> Unit)? = null
) : Dialog(context!!) {

    companion object {
        const val CONFIRM = 1
        const val OPT = 2
    }

    private lateinit var tvContent: TextView
    private var tvYes: TextView? = null
    private var tvCancel: TextView? = null
    private var stringContent: Any? = null
    private var stringYes: String? = null
    private var stringCancel: String? = null

    private var tvContent2: TextView? = null
    private var stringContent2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //#10828
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.alert_common_opt_dialog_backup)
        setCancelable(false)
        initListener()
    }

    private fun initListener() {
        findViewById<View>(R.id.view_yes).setOnClickListener {
            if (clickYesListener != null) {
                clickYesListener?.invoke(this)
            } else {
                dismiss()
            }
        }

        findViewById<View>(R.id.view_cancel).setOnClickListener {
            if (clickCancelListener != null) {
                clickCancelListener?.invoke(this)
            } else {
                dismiss()
            }
        }
    }

    fun setOnClickYesListener(cb: (dialog: Dialog) -> Unit): AlertCommonDialog {
        this.clickYesListener = cb
        return this
    }

    fun setOnClickCancelListener(cb: (dialog: Dialog) -> Unit): AlertCommonDialog {
        this.clickCancelListener = cb
        return this
    }

    fun setCancelableDialog(cancelable: Boolean): AlertCommonDialog {
        setCancelable(cancelable)
        return this
    }

    fun setContent(content: Int): AlertCommonDialog {
        this.stringContent = context.getString(content)
        return this
    }

    fun setContent(content: String?): AlertCommonDialog {
        this.stringContent = content
        return this
    }

    fun setContent(content: Any?): AlertCommonDialog {
        this.stringContent = content
        return this
    }

    fun setContent2(content: Int): AlertCommonDialog {
        this.stringContent2 = context.getString(content)
        return this
    }


    fun setTextButtonYes(text: Int): AlertCommonDialog {
        this.stringYes = context.getString(text)
        return this
    }

    fun setTextButtonYes(text: String): AlertCommonDialog {
        this.stringYes = text
        return this
    }

    fun setTextButtonCancel(text: Int): AlertCommonDialog {
        this.stringCancel = context.getString(text)
        return this
    }

    fun setTextButtonCancel(text: String): AlertCommonDialog {
        this.stringCancel = text
        return this
    }

    override fun show() {
        super.show()

        //BEGIN: set dialog width match parent
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val attr = window?.attributes
        attr?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = attr
        //END: set dialog width match parent
    }

}