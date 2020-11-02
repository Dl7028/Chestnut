package com.yks.chestnutyun.views.mine.usercenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.databinding.ActivityUserCenterBinding

class UserCenterActivity : BaseActivity() {

    private lateinit var ucBinding:ActivityUserCenterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ucBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_center)
        initListener()

    }

    private fun initView() {
        TODO("Not yet implemented")
    }

    private fun initListener() {
        ucBinding.personalBack.setOnClickListener{
            finish()
        }
    }
}