package com.yks.chestnutyun.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.entity.LocalImageInfo
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.LoginViewModel
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
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initListener()
    }

    private fun initView() {

        //轮播图图片
        val localImageInfoList:MutableList<LocalImageInfo> = ArrayList<LocalImageInfo>()
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_car))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_chaojimali))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_xingqiu))
        Log.d("", "" + localImageInfoList.size)
        banner.setBannerData(localImageInfoList)
    }



    private fun initListener() {
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
            subscribeLogin()
        }

    }

    //观察登录结果
    private fun subscribeLogin(){
        val username = loginPhoneInput.text.toString()
        val password = loginPasswordInput.text.toString()
        checkMessage(username, password)
        viewModel.loginResult.observe(this){
            when(it){
                is ResultState.Success<String> ->{
                    ToastUtils.showToast(this,"登录成功")
                    Log.d(TAG, "登录成功$it")
                }
                else -> {
                    ToastUtils.showToast(this,it.toString())
                    Log.d(TAG, "登录失败$it")
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
    ) {
        val ifPhoneNumber = RegExpUtils.checkPhone(username)
        val ifEmailAddress = RegExpUtils.checkEmail(username)
        when{
            !ifPhoneNumber && !ifEmailAddress -> {
                ToastUtils.showToast(this, "用户名格式不合法")
            }
            else -> {
                viewModel.toLogin(username, password)
            }
        }
    }



}