package com.yks.chestnutyun.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.R
import com.yks.chestnutyun.app.BaseActivity
import com.yks.chestnutyun.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initListener()
    }

    override fun initView() {
         binding = DataBindingUtil.setContentView(this,R.layout.activity_register)
    }

    override fun initListener() {
        binding.registerChange.setOnClickListener {
            when(binding.registerTitle.text){
                "邮箱注册" ->{
                    binding.phoneRegisterLayout.visibility = View.GONE
                    binding.emailRegisterLayout.visibility = View.VISIBLE
                    binding.registerChange.text = "切换手机注册"
                    binding.registerTitle.text = "邮箱号注册"
                }
                "手机号注册" ->{
                    binding.phoneRegisterLayout.visibility = View.VISIBLE
                    binding.emailRegisterLayout.visibility = View.GONE
                    binding.registerChange.text = "切换邮箱注册"
                    binding.registerTitle.text = "手机号注册"

                }
            }

        }

    }
}