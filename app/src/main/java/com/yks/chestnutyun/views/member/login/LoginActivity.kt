package com.yks.chestnutyun.views.member.login

import android.os.Bundle
import androidx.activity.viewModels
import com.yks.chestnutyun.R
import com.yks.chestnutyun.views.base.BaseActivity
import com.yks.chestnutyun.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
  * @Description:    登录界面的Activity
  * @Author:         Yu ki-r
  * @CreateDate:     ${DATE} ${TIME}
 */

@AndroidEntryPoint
class LoginActivity(): BaseActivity() {

    private companion object val TAG: String?="LoginActivity"
    private val viewModel: LoginViewModel by viewModels()


    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun startObserve() {
    }


    }









