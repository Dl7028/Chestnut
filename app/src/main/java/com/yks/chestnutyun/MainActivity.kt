package com.yks.chestnutyun

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.base.BaseActivity
import com.yks.chestnutyun.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private companion object val TAG  = "MainActivity"
    private   var navController:NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController =supportFragmentManager.findFragmentById(R.id.nav_host_main)?.findNavController()
        initView()
        initListener()
    }


    override fun initView() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    override fun initListener() {



        mainBinding.bottomNavigationMain.setNavigationChangeListener { _, position ->
            Log.d(TAG, "" + position)

            when(position){
                0 -> {
                    Log.d(TAG, "点击了文件")
                    findNavController(this,R.id.nav_host_main).navigate(R.id.nav_file_fragment)


                }
                1 -> {
                    Log.d(TAG, "点击了主页")
                    findNavController(this,R.id.nav_host_main).navigate(R.id.nav_home_fragment)


                }
                2 -> {
                    Log.d(TAG, "点击了我的")
                    findNavController(this,R.id.nav_host_main).navigate(R.id.nav_mine_fragment)
                }



            }
        }
    }

}