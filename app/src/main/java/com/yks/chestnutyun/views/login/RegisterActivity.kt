package com.yks.chestnutyun.views.login

import android.os.Bundle
import androidx.activity.viewModels
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*

/**
  * @Description:    注册功能的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/2 22:09
 */

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private companion object

    val TAG: String = "RegisterActivity"
    private val viewModel: LoginViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入

    override fun setLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView(savedInstanceState: Bundle?) {
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
    override fun startObserve() {
        viewModel.mRegisterStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.register_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(this,"注册成功"+it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(this,"注册失败"+it.showError)
            }
        }
        viewModel.mGetCodeStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.getCode_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(this,"获取验证码成功"+it.showEnd)  //请求成功
                loginEmailGetCodeButton.start() //获取成功，计时开始
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(this,"获取验证码失败"+it.showError)
            }
        }

    }


    /**
     * 获取验证码
     */
    private fun getCode() {
        val ifPhoneNumber = RegExpUtils.checkPhone(registerEmailPhoneInput.text.toString())
        val ifEmailAddress = RegExpUtils.checkEmail(registerEmailPhoneInput.text.toString())
        if (ifPhoneNumber || ifEmailAddress) {  //用户名合法，获取验证码并观察结果
            viewModel.getCode(registerEmailPhoneInput.text.toString())

        } else {
            ToastUtils.showToast(this, "请输入正确的用户名") //用户名不合法
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
        val ifPhoneNumber = RegExpUtils.checkPhone(name)
        val ifEmailAddress = RegExpUtils.checkEmail(name)
        //检查信息格式是否合法
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
                viewModel.register(name, password, verificationCode)
            }
        }
    }
}




