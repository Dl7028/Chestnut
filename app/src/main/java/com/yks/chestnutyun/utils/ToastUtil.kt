package com.yks.chestnutyun.utils

import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.yks.chestnutyun.app.MyApplication.Companion.CONTEXT

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/17 11:09
 */
object ToastUtil {


    private var toast: Toast? = null

    fun ToastUtil() {}

    fun showToast(msg: String) {
        if ("main" == Thread.currentThread().name) {
            createToast(msg)
        } else {
            ActivityUtil.getCurrentActivity()!!.runOnUiThread {
                ToastUtil.createToast(msg)
            }
        }
    }

    @SuppressLint("ShowToast")
    private fun createToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(CONTEXT, msg, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(msg)
        }
        val linearLayout = toast!!.view as LinearLayout?
        val messageTextView = linearLayout!!.getChildAt(0) as TextView
        messageTextView.textSize = 15.0f
        toast!!.show()
    }

    fun showCenterToast(msg: String) {
        if ("main" == Thread.currentThread().name) {
            createCenterToast(msg)
        } else {
            ActivityUtil.getCurrentActivity()!!.runOnUiThread {
              ToastUtil.createCenterToast(
                    msg
                )
            }
        }
    }

    @SuppressLint("ShowToast")
    private fun createCenterToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(CONTEXT, msg, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(msg)
        }
        val linearLayout = toast!!.view as LinearLayout?
        val messageTextView = linearLayout!!.getChildAt(0) as TextView
        toast!!.setGravity(17, 0, 0)
        messageTextView.textSize = 15.0f
        toast!!.show()
    }

    fun cancelToast() {
        if (toast != null) {
            toast!!.cancel()
        }
    }
}