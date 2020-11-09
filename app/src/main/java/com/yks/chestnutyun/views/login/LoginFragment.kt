package com.yks.chestnutyun.views.login

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.entity.LocalImageInfo
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.LoginViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:24
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private companion object val TAG: String?="LoginFragment"
    private val viewModel: LoginViewModel by viewModels()

    override fun setLayoutResId(): Int = R.layout.fragment_login


    override fun initView() {
        //轮播图图片
        val localImageInfoList:MutableList<LocalImageInfo> = ArrayList<LocalImageInfo>()
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_car))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_chaojimali))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_xingqiu))

        banner.setBannerData(localImageInfoList)

        //跳转到注册
        loginRegisterButton.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
            Navigation.findNavController(it).navigate(R.id.action_nav_login_fragment_to_nav_register_fragment)
        }
        //加载轮播图
        banner.loadImage { banner: XBanner, model, view: View, position ->
            (view as ImageView).setImageResource((model as LocalImageInfo).xBannerUrl)
        }

        loginButton.setOnClickListener {
            login()
        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        viewModel.mLoginStatus.observe(this) {
            if (it.showLoading) showProgressDialog(R.string.register_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(activity,"登录成功"+it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity,"登录失败"+it.showError)
            }
        }
    }
    private fun login() {
        val username = loginPhoneInput.text.toString()
        val password = loginPasswordInput.text.toString()
        val ifPhoneNumber = RegExpUtils.checkPhone(username)
        val ifEmailAddress = RegExpUtils.checkEmail(username)
        when {
            !ifPhoneNumber && !ifEmailAddress -> {
                ToastUtils.showToast(activity, "用户名格式不合法")
            }
            else -> {
                viewModel.login(username, password)
            }
        }
    }
}