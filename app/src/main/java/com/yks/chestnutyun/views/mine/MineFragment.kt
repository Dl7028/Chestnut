package com.yks.chestnutyun.views.mine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
        import com.yks.chestnutyun.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentMineBinding
import com.yks.chestnutyun.views.mine.usercenter.UserCenterActivity

/**
 * @Description:    我的
 * fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 23:03
 */
class MineFragment:BaseFragment() {

    private lateinit var mineDataBinding:FragmentMineBinding
    companion object val TAG = "MineFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         mineDataBinding = FragmentMineBinding.inflate(inflater, container, false)
        context ?: return mineDataBinding.root
        initListener()

        return mineDataBinding.root
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initListener() {


        mineDataBinding.mineCenterButton.setOnClickListener {
//            findNavController().navigate(R.id.nav_user_center_fragment)
            startActivity(Intent(activity, UserCenterActivity::class.java))
            Log.d(TAG, "点击了按钮")
        }
    }
}






