package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.play.core.internal.e

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
class RegisterActivity : AppCompatActivity() {


    private companion object val TAG: String? = "RegisterActivity"
     private val viewModel: RegisterViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val binding: ActivityRegisterBinding =  DataBindingUtil.setContentView<ActivityRegisterBinding>(this,R.layout.activity_register)
        binding.lifecycleOwner = this //将LifecycleOwner设置为能够观察LiveData对象
        binding.viewModel =viewModel //DataBinding 绑定布局中的ViewModel 与 RegisterViewModel对象关联


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
            subscribeUi(binding)
        }
    }


    private fun subscribeUi(binding:ActivityRegisterBinding) {
        try {
            // 观察是否注册成功
            viewModel.toRegister().observe(this) {
                Log.d(TAG, "result--------")
            }
            //观察进度条是否显示
            viewModel.ifProgressShow.observe(this) {
                if (it){
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            e.message?.apply {
                viewModel.setToast(this)
            }
            e.printStackTrace()
        }

        viewModel.mToastString.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.mToastString.value = ""
            }
        }
    }
    }



