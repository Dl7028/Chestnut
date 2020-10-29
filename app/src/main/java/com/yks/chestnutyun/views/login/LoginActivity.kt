package com.yks.chestnutyun.views.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.yks.chestnutyun.R
import com.yks.chestnutyun.app.BaseActivity
import com.yks.chestnutyun.databinding.ActivityLoginBinding
import com.yks.chestnutyun.views.RegisterActivity

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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
    }

    override fun initListener() {

        binding.loginRegisterButton.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

}