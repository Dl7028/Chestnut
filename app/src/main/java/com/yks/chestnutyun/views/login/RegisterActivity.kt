package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil

import com.yks.chestnutyun.R
import com.yks.chestnutyun.app.BaseActivity
import com.yks.chestnutyun.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initListener()
    }

    override fun initView() {
         registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        Log.d("tag",""+registerBinding.registerTitle.text)
    }

    override fun initListener() {
        //点击更改注册方式
        registerBinding.registerChange.setOnClickListener {
            registerMethod(registerBinding)
        }
        //点击取消注册
        registerBinding.registerCancel.setOnClickListener {
            finish()
        }

    }


    /**
     * 更改注册的方式，手机号or邮箱号
     */
    private fun registerMethod(binding: ActivityRegisterBinding){
        when(binding.registerTitle.text){
            "手机号注册" ->{
                binding.phoneRegisterLayout.visibility = View.GONE
                binding.emailRegisterLayout.visibility = View.VISIBLE
                binding.registerChange.text = "切换手机号注册"
                binding.registerTitle.text = "邮箱号注册"
            }
            "邮箱号注册" ->{
                binding.phoneRegisterLayout.visibility = View.VISIBLE
                binding.emailRegisterLayout.visibility = View.GONE
                binding.registerChange.text = "切换邮箱号注册"
                binding.registerTitle.text = "手机号注册"
            }

        }
    }
}