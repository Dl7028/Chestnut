package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil

import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.databinding.ActivityRegisterBinding
import com.yks.chestnutyun.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
  * @Description:    注册功能的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/2 22:09
 */

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private var mUsername:String? = null
    private var mPassword:String? = null
    private var mEmail:String? = null
    private var mVerificationCode:String? = null
    private val viewModel: RegisterViewModel by viewModels() //Activity 持有 ViewModel 的对象 ，Hilt 注入



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val binding: ActivityRegisterBinding =  DataBindingUtil.setContentView(this,R.layout.activity_register)

        initView(binding)
        initListener(binding)

    }

    private fun initView(binding:ActivityRegisterBinding) {
    }

    private fun initListener(binding:ActivityRegisterBinding) {

        //点击退出注册
        binding.registerCancel.setOnClickListener {
            finish()
        }



        //点击注册
        binding.registerButton.setOnClickListener {
        }


    }


  /*  *//**
     * 更改注册的方式，手机号or邮箱号
     *//*
    private fun registerMethod(binding: ActivityRegisterBinding){
        when(binding.registerTitle.text){
            getString(R.string.register_phone_title) ->{
                binding.phoneRegisterLayout.visibility = View.GONE
                binding.emailRegisterLayout.visibility = View.VISIBLE
                binding.registerChange.text = getString(R.string.change_to_phone_register)
                binding.registerTitle.text =  getString(R.string.register_emil_title)
            }
            getString(R.string.register_emil_title) ->{
                binding.phoneRegisterLayout.visibility = View.VISIBLE
                binding.emailRegisterLayout.visibility = View.GONE
                binding.registerChange.text = getString(R.string.change_emil_register)
                binding.registerTitle.text =  getString(R.string.register_phone_title)
            }

        }
    }*/

    private fun subscribeUi(username:String, password:String,verificationCode:String){
        viewModel.toRegisters(username,password,verificationCode).observe(this){
            if (it == true){
                //注册成功
            }else{
                //注册失败
            }
        }
    }



}