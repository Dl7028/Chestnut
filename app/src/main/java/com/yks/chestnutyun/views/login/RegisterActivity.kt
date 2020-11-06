package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.R
import com.yks.chestnutyun.databinding.ActivityRegisterBinding
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
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
        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
        initView(binding)
        initListener(binding)
    }

    private fun initView(binding: ActivityRegisterBinding) {
        binding.lifecycleOwner = this //将LifecycleOwner设置为能够观察LiveData对象
        binding.viewModel = viewModel //DataBinding 绑定布局中的ViewModel 与 RegisterViewModel对象关联
    }

    private fun initListener(binding: ActivityRegisterBinding) {

        //点击退出注册
        binding.registerCancel.setOnClickListener {
            finish()
        }


        //点击注册
        binding.registerButton.setOnClickListener {
            subscribeRegisters(binding)

        }
        //获取验证码
        binding.loginEmailGetcodeButton.setOnClickListener {
            subscribeGetCode(binding)


        }

    }

    // 获取验证码
    private fun subscribeGetCode(binding: ActivityRegisterBinding) {
        binding.loginEmailGetcodeButton.isClickable = false
        val ifPhoneNumber = RegExpUtils.checkPhone(binding.registerEmailPhoneInput.text.toString())
        val ifEmailAddress = RegExpUtils.checkEmail(binding.registerEmailPhoneInput.text.toString())

        if (ifPhoneNumber || ifEmailAddress) {
            binding.loginEmailGetcodeButton.start()
            viewModel.getCode(binding.registerEmailPhoneInput.text.toString()).observe(this) {
                if (it == true) {
                    Log.d(TAG, "获取验证码成功")
                    binding.loginEmailGetcodeButton.isClickable = true
                    ToastUtils.showToast(this, "验证码已发送，请注意查收")
                }
            }
        } else {
            ToastUtils.showToast(this, "请输入正确的用户名")
            binding.loginEmailGetcodeButton.isClickable = true

        }
    }


    private fun subscribeRegisters(binding: ActivityRegisterBinding) {
        val name = binding.registerEmailPhoneInput.text.toString()
        val verificationCode = binding.registerEmailCodeInput.text.toString()
        val password = binding.registerPasswordInput.text.toString()
        val rePassword = binding.registerConfirmPasswordInput.text.toString()
        //检查信息格式是否合法
        if (checkMessage(name, verificationCode, password,rePassword)) {
            //显示加载框
            binding.progressBar.visibility = View.VISIBLE
            // 观察是否注册成功
            viewModel.toRegister(name, verificationCode, password).observe(this) {
                if (it == true) {
                    //注册成功
                    //隐藏加载条
                    binding.progressBar.visibility = View.GONE

                } else {
                    //注册失败
                    //隐藏加载条
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }


    /**
     * 检查信息输入是否合法
     */
    private fun checkMessage(username:String,verificationCode:String,password:String,rePassword:String): Boolean {
        val ifPhoneNumber = RegExpUtils.checkPhone(username)
        val ifEmailAddress = RegExpUtils.checkEmail(username)

        when{
            !ifPhoneNumber && !ifEmailAddress -> {
                ToastUtils.showToast(this, "用户名格式不合法")
                return false
            }
            verificationCode.isEmpty() -> {
                ToastUtils.showToast(this, "验证码不能为空")
                return false
            }
            password != rePassword -> {
                ToastUtils.showToast(this, "密码不一样")
                return false
            }

        }
        return true
    }
}





