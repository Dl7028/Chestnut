package com.yks.chestnutyun.views.member.fragment

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.entity.LocalImageInfo
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.ext.setOnClickWithFilter
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.LoginViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Description:    登录的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:24
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var sp: SharedPreferences
    private companion object val TAG: String?="LoginFragment"
//    private val args :RegisterFragmentArgs by navArgs()

    private  val viewModel:LoginViewModel by viewModels<LoginViewModel>()



    override fun setLayoutResId(): Int = R.layout.fragment_login


    override fun initView() {
        sp = requireActivity().getSharedPreferences("userInfo",MODE_PRIVATE)
        //开启应用的时候，显示保存的用户信息
        val name = sp.getString("name","").toString()
        val password =sp.getString("password","").toString()
        loginPhoneInput.setText(name)
        loginPasswordInput.setText(password)
        if(name.isNotEmpty()&&password.isNotEmpty()){
            login()
        }
        //轮播图图片
        val localImageInfoList:MutableList<LocalImageInfo> = ArrayList<LocalImageInfo>()
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_car))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_chaojimali))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_xingqiu))

        banner.setBannerData(localImageInfoList)
        //跳转到注册
        loginRegisterButton.setOnClickWithFilter {
            dismissProgressDialog()
            it.findNavController().navigate(R.id.action_nav_login_fragment_to_nav_register_fragment)
        }

        //加载轮播图
        banner.loadImage { banner: XBanner, model, view: View, position ->
            (view as ImageView).setImageResource((model as LocalImageInfo).xBannerUrl)
        }

        loginButton.setOnClickWithFilter {
            login()
        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        viewModel.mLoginStatus.observe(this) {
//            if (it.showLoading) showProgressDialog(R.string.login_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                //登录成功
                //1.保存用户信息
                saveUser(loginPhoneInput.text.toString(),loginPasswordInput.text.toString())
                ToastUtil.showToast(it.showEnd.toString())  //请求成功
                //2.跳转到主页面
                goToMainActivity()
//                requireActivity().finish()

            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
            dismissProgressDialog()
        }
    }

    /**
     *
     * 跳转到主页面
     */
    private fun goToMainActivity() {
        val intent = Intent()
        intent.putExtra("username", loginPhoneInput.text.toString())
        intent.putExtra("password", loginPasswordInput.text.toString())
        intent.setClass(requireActivity(), MainActivity::class.java)
        requireActivity().startActivity(intent)
    }

    /**
     * 开始登录
     */
    private fun login() {
        val username = loginPhoneInput.text.toString()
        val password = loginPasswordInput.text.toString()
        val ifPhoneNumber = RegExpUtils.checkPhone(username)
        val ifEmailAddress = RegExpUtils.checkEmail(username)
        when {
            !ifPhoneNumber && !ifEmailAddress -> {
                ToastUtil.showToast("用户名格式不合法")
            }
            else -> {
                viewModel.login(username, password)
            }
        }
    }


    /**
     *  保存用户信息
     *
     * @param username 用户名
     * @param password 密码
     */
    @SuppressLint("CommitPrefEdits")
    private fun saveUser(username: String, password: String){
        val editor = sp.edit()
        //保存用户信息
        editor.putString("name",username)
        editor.putString("password",password)
        editor.apply() //提交

    }


}