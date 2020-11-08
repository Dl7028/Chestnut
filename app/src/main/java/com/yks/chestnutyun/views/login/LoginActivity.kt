package com.yks.chestnutyun.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.entity.LocalImageInfo
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.LoginViewModel
import com.yks.chestnutyun.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

/**
  * @Description:    登录界面的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     ${DATE} ${TIME}
 */

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private companion object val TAG: String?="LoginActivity"
    private val viewModel: RegisterViewModel by viewModels()


    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
        //轮播图图片
        val localImageInfoList:MutableList<LocalImageInfo> = ArrayList<LocalImageInfo>()
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_car))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_chaojimali))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_xingqiu))
        Log.d("", "" + localImageInfoList.size)
        banner.setBannerData(localImageInfoList)

        //跳转到注册
        loginRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //跳转到主页
        loginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
        //加载轮播图
        banner.loadImage { banner: XBanner, model, view: View, position ->
            (view as ImageView).setImageResource((model as LocalImageInfo).xBannerUrl)
        }

        loginButton.setOnClickListener {
            login()
        }
    }

    override fun startObserve() {
        viewModel.mLoginStatus.observe(this) {
            if (it.showLoading) showProgressDialog(R.string.register_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(this,"登录成功"+it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(this,"登录失败"+it.showError)
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
                ToastUtils.showToast(this, "用户名格式不合法")
            }
            else -> {
                viewModel.login(username, password)
            }
        }
    }










}