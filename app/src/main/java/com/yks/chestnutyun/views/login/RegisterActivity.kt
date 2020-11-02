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

    private val viewModel: RegisterViewModel by viewModels()

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
    }

    private fun subscribeUi(){
        viewModel.registerResult.observe(this){
            if (it == true){
                //注册成功
            }else{
                //注册失败
            }
        }
    }



}