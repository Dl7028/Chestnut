package com.yks.chestnutyun.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.TextView

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/6 11:27
 */

class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long, button: TextView) : CountDownTimer(
    millisInFuture,
    countDownInterval
){

    val mTextView : TextView = button

    //计时过程
    @SuppressLint("SetTextI18n")
    override fun onTick(l: Long) {
        mTextView.isClickable = false
        mTextView.text = "${l/1000} 秒"

    }

    //计时结束
    override fun onFinish() {
        mTextView.text = "重新获取"
        mTextView.isClickable   = true
    }



}
