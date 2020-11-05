package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.R
import com.yks.chestnutyun.databinding.ActivityRegisterBinding
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
  * @Description:    注册功能的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/2 22:09
 */

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {


    private companion object

    val TAG: String? = "RegisterActivity"
    private val viewModel: RegisterViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入
    private val patternMailBox: Pattern =
        Pattern.compile("/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$/") // 正则表达的式匹配邮箱
    private val patternTell: Pattern = Pattern.compile("^1[0-9]{10}") // 正则表达式匹配手机号


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val binding: ActivityRegisterBinding =
            DataBindingUtil.setContentView<ActivityRegisterBinding>(
                this,
                R.layout.activity_register
            )
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
            subscribeUi(binding)

        }
        binding.loginEmailGetcodeButton.setOnClickListener {
            
        }

    }





    private fun subscribeUi(binding: ActivityRegisterBinding) {
        val name = binding.registerEmailPhoneInput.text.toString()
        val verificationCode = binding.registerEmailCodeInput.text.toString()
        val password = binding.registerPasswordInput.text.toString()
        if (checkMessage(name, verificationCode, password, binding)) {

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
        } else {
            //信息错误
        }
    }


    /**
     * 检查信息输入是否合法
     */
    private fun checkMessage(username:String,password:String,verificationCode:String,binding: ActivityRegisterBinding): Boolean {
        val ifPhoneNumber = RegExpUtils.checkPhone(binding.registerEmailPhoneInput.text.toString())
        val ifEmailAddress = RegExpUtils.checkEmail(binding.registerEmailPhoneInput.text.toString())
        val rePassword = binding.registerConfirmPasswordInput.text.toString()

        if (!ifPhoneNumber && !ifEmailAddress) {
            ToastUtils.showToast(this, "用户名不能为空")
            return false
        }
        if (verificationCode.isEmpty()) {
            ToastUtils.showToast(this, "验证码不能为空")
            return false

        }
        if (password != rePassword) {         //判断两次密码是否相同
            ToastUtils.showToast(this, "密码不一样")
            return false

        }
        return true
    }
}





