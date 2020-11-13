package com.yks.chestnutyun.views.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yks.chestnutyun.R
import com.yks.chestnutyun.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setLayoutId(): Int  = R.layout.activity_user

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun startObserve() {
    }
}