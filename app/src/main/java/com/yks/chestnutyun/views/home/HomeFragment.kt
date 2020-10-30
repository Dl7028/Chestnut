package com.yks.chestnutyun.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseFragment

/**
 * @Description:    主页fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 9:05
 */
class HomeFragment:BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initListener() {
        TODO("Not yet implemented")
    }
}