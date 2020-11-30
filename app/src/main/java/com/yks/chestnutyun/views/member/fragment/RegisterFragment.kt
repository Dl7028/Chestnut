package com.yks.chestnutyun.views.member.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.CustomDialog
import com.yks.chestnutyun.ext.setOnClickWithFilter
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.LoginViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * @Description:    注册的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:25
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment(){

    private companion object

    val TAG: String = "RegisterFragment"
    private val viewModel: LoginViewModel by viewModels()   //Activity 持有 ViewModel 的对象 ，Hilt 注入
    private   lateinit var mDialog:CustomDialog

    override fun setLayoutResId(): Int = R.layout.fragment_register

    override fun initView() {
        //点击退出注册
        registerCancel.setOnClickWithFilter {
           findNavController().navigateUp()
        }

        //获取验证码
        loginEmailGetCodeButton.setOnClickWithFilter {
            getCode()
        }

        //点击注册
        registerButton.setOnClickWithFilter {
            registers()
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.mRegisterStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.register_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtil.showToast(it.showEnd.toString())  //请求成功
                showLayoutDialog()
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast( it.showError)
            }
        }
        viewModel.mGetCodeStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.getCode_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtil.showToast( it.showEnd.toString())  //请求成功
                loginEmailGetCodeButton.start() //获取成功，计时开始
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast( it.showError)
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
            ToastUtil.showToast("请输入正确的用户名") //用户名不合法
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
                ToastUtil.showToast( "用户名格式不合法")

            }
            verificationCode.isEmpty() -> {
                ToastUtil.showToast("验证码不能为空")

            }
            password.isEmpty()||rePassword.isEmpty() -> {
                ToastUtil.showToast("密码不能为空")
            }

            password != rePassword   -> {
                ToastUtil.showToast( "密码不一样")
            }
            else -> {
                viewModel.register(name, password, verificationCode)
            }
        }
    }

    /**
     * 自定义对话框
     */
    private  fun showLayoutDialog() {
        mDialog = CustomDialog(activity,"注册成功！","是否去登录？", {
            //取消登录

            mDialog.dismiss()
            }, {
            //确认登录
            ToastUtil.showToast( "确认登录")
//            Snackbar.make(it,"确认登录",Snackbar.LENGTH_LONG).show()
            findNavController().navigateUp()
            mDialog.dismiss()
         },"取消","确认")
        mDialog.setCanotBackPress()
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
    }




}