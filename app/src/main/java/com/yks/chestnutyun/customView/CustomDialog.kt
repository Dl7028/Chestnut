package com.yks.chestnutyun.customView

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.yks.chestnutyun.R


/**
 * @Description:    自定义对话框
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 21:52
 */

class CustomDialog : Dialog {
    private var title: String
    private var content: String
    private var buttonConfirm: String
    private var buttonCancel: String? = null
    private var confirmClickListener: View.OnClickListener?
    private var cancelClickListener: View.OnClickListener? = null
    private var show = 2

    constructor(
        context: Context?, title: String, content: String,
        buttonConfirm: String,
        confirmClickListener: View.OnClickListener?
    ) : super(context!!, R.style.Dialog) {
        this.title = title
        this.content = content
        this.buttonConfirm = buttonConfirm
        this.confirmClickListener = confirmClickListener
        show = SHOW_ONE
    }

    constructor(
        context: Context?, title: String, content: String,
        buttonConfirm: String,
        confirmClickListener: View.OnClickListener?, buttonCancel: String?
    ) : super(context!!, R.style.Dialog) {
        this.title = title
        this.content = content
        this.buttonConfirm = buttonConfirm
        this.buttonCancel = buttonCancel
        this.confirmClickListener = confirmClickListener
    }

    constructor(
        context: Context?, title: String, content: String,
        confirmClickListener: View.OnClickListener?, buttonConfirm: String, buttonCancel: String?
    ) : super(context!!, R.style.Dialog) {
        this.title = title
        this.content = content
        this.buttonConfirm = buttonConfirm
        this.buttonCancel = buttonCancel
        this.confirmClickListener = confirmClickListener
    }

    constructor(
        context: Context?,
        title: String,
        content: String,
        confirmClickListener: View.OnClickListener?,
        cancelClickListener: View.OnClickListener?,
        buttonConfirm: String,
        buttonCancel: String?
    ) : super(context!!, R.style.Dialog) {
        this.title = title
        this.content = content
        this.buttonConfirm = buttonConfirm
        this.buttonCancel = buttonCancel
        this.confirmClickListener = confirmClickListener
        this.cancelClickListener = cancelClickListener
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
        val dialog_title = findViewById<TextView>(R.id.dialog_title)
        val dialog_content = findViewById<TextView>(R.id.dialog_content)
        val dialog_confirm = findViewById<TextView>(R.id.dialog_confirm)
        val dialog_cancel = findViewById<TextView>(R.id.dialog_cancel)
        val dialog_line: View = findViewById(R.id.dialog_line)
        if (!TextUtils.isEmpty(title)) dialog_title.text = title
        if (!TextUtils.isEmpty(content)) {
            dialog_content.text = content
        } else {
            dialog_content.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(buttonConfirm)) dialog_confirm.text = buttonConfirm
        if (!TextUtils.isEmpty(buttonCancel)) dialog_cancel.text = buttonCancel
        if (SHOW_ONE == show) {
            dialog_line.visibility = View.GONE
            dialog_cancel.visibility = View.GONE
            if (null != confirmClickListener) {
                dialog_confirm.setOnClickListener(confirmClickListener)
            }
            dialog_confirm.setBackgroundResource(R.drawable.back_text_selector_left)
        } else {
            if (null != confirmClickListener) {
                dialog_confirm.setOnClickListener(confirmClickListener)
            }
            if (null != cancelClickListener) {
                dialog_cancel.setOnClickListener(cancelClickListener)
            } else {
                dialog_cancel.setOnClickListener { this@CustomDialog.dismiss() }
            }
        }
    }

    fun setCanotBackPress() {
        this.setOnKeyListener { _, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && event.action === KeyEvent.ACTION_UP }
    }

    companion object {
        private const val SHOW_ONE = 1
    }
}