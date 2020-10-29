package com.yks.chestnutyun.views.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.entity.LocalImageInfo
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.app.BaseActivity
import com.yks.chestnutyun.databinding.ActivityLoginBinding

/**
  * @Description:    登录界面的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     ${DATE} ${TIME}
 */
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding  //声明FragmentGardenBinding 对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }

    override fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //轮播图图片
        val localImageInfoList:MutableList<LocalImageInfo> = ArrayList<LocalImageInfo>()
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_car))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_chaojimali))
        localImageInfoList.add(LocalImageInfo(R.mipmap.banner_xingqiu))
        Log.d("", "" + localImageInfoList.size)
        binding.banner.setBannerData(localImageInfoList)
    }



    override fun initListener() {
        //跳转到注册
        binding.loginRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        //跳转到主页
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
        //加载轮播图
        binding.banner.loadImage { banner: XBanner, model, view: View, position ->
            (view as ImageView).setImageResource((model as LocalImageInfo).xBannerUrl)
        }

    }

}