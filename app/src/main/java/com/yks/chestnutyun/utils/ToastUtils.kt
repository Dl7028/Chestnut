package com.yks.chestnutyun.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast




/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/5 21:23
 */
object ToastUtils {
    private val context: Context? = null
    private var toast: Toast? = null
    @SuppressLint("ShowToast")
    fun showToast(context: Context?, text: String?) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        toast!!.show()
    }
}