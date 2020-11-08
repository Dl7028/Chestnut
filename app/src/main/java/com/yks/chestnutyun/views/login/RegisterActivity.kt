package com.yks.chestnutyun.views.login

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.yks.chestnutyun.R
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*
import java.util.Arrays.toString

/**
  * @Description:    注册功能的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/2 22:09
 */

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private companion object

    val TAG: String = "RegisterActivity"
    private val viewModel: RegisterViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        //点击退出注册
        registerCancel.setOnClickListener {
            finish()
        }

        //获取验证码
        loginEmailGetCodeButton.setOnClickListener {
            getCode()
        }

        //点击注册
        registerButton.setOnClickListener {
            registers()
        }
    }


    // 获取验证码
    private fun getCode() {
        val ifPhoneNumber = RegExpUtils.checkPhone(registerEmailPhoneInput.text.toString())
        val ifEmailAddress = RegExpUtils.checkEmail(registerEmailPhoneInput.text.toString())
        if (ifPhoneNumber || ifEmailAddress) {  //用户名合法，获取验证码并观察结果
            loginEmailGetCodeButton.start() //获取成功，计时开始
            subscribeGetCode()
        } else {
            ToastUtils.showToast(this, "请输入正确的用户名") //用户名不合法
        }
    }

    /**
     * 观察验证码获取情况
     */
    private fun subscribeGetCode() {

        viewModel.getCode(registerEmailPhoneInput.text.toString()).observe(this) {
            if (it == true) {
                ToastUtils.showToast(this, "验证码已发送，请注意查收")
            }
        }

    }


    /**
     * 开始注册
     */
    private fun registers() {
        val name = registerEmailPhoneInput.text.toString()
        val verificationCode = registerEmailCodeInput.text.toString()
        val password = registerPasswordInput.text.toString()
        val rePassword = registerConfirmPasswordInput.text.toString()
        //检查信息格式是否合法
        checkMessage(name, password, verificationCode, rePassword)
            //显示加载框

            // 观察是否注册成功
        subscribeRegister(name, password, verificationCode)

    }


    //观察注册结果
    private fun subscribeRegister(
        name: String,
        password: String,
        verificationCode: String,
    ) {

        viewModel.registerResult.observe(this){
            when(it){
                is ResultState.Success<String> ->{
                    ToastUtils.showToast(this,"注册成功")
                    progressBar.visibility = View.GONE

                    Log.d(TAG, "注册成功$it")
                }
                else -> {
                    ToastUtils.showToast(this,it.toString())
                    progressBar.visibility = View.GONE

                    Log.d(TAG, "注册失败$it")
                }
            }
        }

    }


    /**
     * 检查信息输入是否合法
     */
    private fun checkMessage(
        username: String,
        password: String,
        verificationCode: String,
        rePassword: String
    ) {
        val ifPhoneNumber = RegExpUtils.checkPhone(username)
        val ifEmailAddress = RegExpUtils.checkEmail(username)
          when{
            !ifPhoneNumber && !ifEmailAddress -> {
                ToastUtils.showToast(this, "用户名格式不合法")

            }
            verificationCode.isEmpty() -> {
                ToastUtils.showToast(this, "验证码不能为空")

            }
            password.isEmpty()||rePassword.isEmpty() -> {
                ToastUtils.showToast(this, "密码不能为空")
            }

            password != rePassword   -> {
                ToastUtils.showToast(this, "密码不一样")
            }
              else -> {
                  viewModel.toRegister(username, password, verificationCode)
                  progressBar.visibility = View.VISIBLE
              }
        }
    }

}




