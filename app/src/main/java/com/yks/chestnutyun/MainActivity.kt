package com.yks.chestnutyun

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import com.gyf.immersionbar.ImmersionBar
import com.yks.chestnutyun.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private companion object val TAG  = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initListener()
    }


    private fun initView() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ImmersionBar.with(this).titleBar(toolbar).init()

    }

    private fun initListener() {

        mainBinding.bottomNavigationMain.setNavigationChangeListener { _, position ->
            Log.d(TAG, "" + position)
            when(position){
                0 -> {
                    findNavController(this,
                        R.id.nav_host_main
                    ).navigate(R.id.files_view_pager_fragment)
                    mainBinding.toolbarTitle.text = "文件"
//                    mainBinding.toolbarLayout.setBackgroundColor(Color.parseColor("#9c27b0"))
                }
                1 -> {
                    findNavController(this, R.id.nav_host_main).navigate(R.id.nav_home_fragment)
                    mainBinding.toolbarTitle.text= "主页"
//                    mainBinding.toolbarLayout.setBackgroundColor(Color.parseColor("#03a9f4"))
                }
                2 -> {
                    findNavController(this, R.id.nav_host_main).navigate(R.id.nav_mine_fragment)
                    mainBinding.toolbarTitle.text = "用户"
//                    mainBinding.toolbarLayout.setBackgroundColor(Color.parseColor("#4caf50"))
                }
            }
        }
    }

}