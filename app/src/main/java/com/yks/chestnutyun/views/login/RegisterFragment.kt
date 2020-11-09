package com.yks.chestnutyun.views.login

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import androidx.navigation.ui.NavigationUI.navigateUp
import com.yks.chestnutyun.R
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.LoginViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:25
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment(){

    private companion object

    val TAG: String = "RegisterFragment"
    private val viewModel: LoginViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入

    override fun setLayoutResId(): Int = R.layout.fragment_register

    override fun initView() {
        //点击退出注册
        registerCancel.setOnClickListener {
           findNavController().navigateUp()
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

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.mRegisterStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.register_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(activity,"注册成功"+it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity,"注册失败"+it.showError)
            }
        }
        viewModel.mGetCodeStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.getCode_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(activity,"获取验证码成功"+it.showEnd)  //请求成功
                loginEmailGetCodeButton.start() //获取成功，计时开始
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity,"获取验证码失败"+it.showError)
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
            ToastUtils.showToast(activity, "请输入正确的用户名") //用户名不合法
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
                ToastUtils.showToast(activity, "用户名格式不合法")

            }
            verificationCode.isEmpty() -> {
                ToastUtils.showToast(activity, "验证码不能为空")

            }
            password.isEmpty()||rePassword.isEmpty() -> {
                ToastUtils.showToast(activity, "密码不能为空")
            }

            password != rePassword   -> {
                ToastUtils.showToast(activity, "密码不一样")
            }
            else -> {
                viewModel.register(name, password, verificationCode)
            }
        }
    }
}