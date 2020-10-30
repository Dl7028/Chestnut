package com.yks.chestnutyun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

    override fun initListener() {
        }

}