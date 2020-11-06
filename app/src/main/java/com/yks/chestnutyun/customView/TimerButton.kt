package com.yks.chestnutyun.customView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.Button
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.yks.chestnutyun.R


@SuppressLint("AppCompatCustomView")
class TimingButton(context: Context?, attrs: AttributeSet?) : Button(context, attrs) {
    private val total: Int
    private val interval: Int
    private val psText: String?

    //执行
    fun start() {
        val time: TimeCount = TimeCount(total.toLong(), interval.toLong())
        time.start()
    }

    inner class TimeCount(millisInFuture: Long, private val countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() { //计时完毕时触发
            text = psText
            isEnabled = true
        }

        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) { //计时过程显示
            isEnabled = false
            text = "${millisUntilFinished/countDownInterval}秒"
        }
    }

    init {
        // 获取自定义属性，并赋值
        val typedArray: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton)
        total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000)
        interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000)
        psText = typedArray.getString(R.styleable.TimingButton_tb_psText)
        setBackgroundResource(R.drawable.timing_button) //设置默认样式
        typedArray.recycle()
    }
}

