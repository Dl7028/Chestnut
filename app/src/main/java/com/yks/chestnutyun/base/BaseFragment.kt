package com.yks.chestnutyun.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @Description:    Fragment 基类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 9:09
 */
abstract class BaseFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化view
        initView()
        initListener()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun initView()


    abstract fun initListener()
}