package com.yks.chestnutyun.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.ChestnutPagerAdapter
import com.yks.chestnutyun.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentViewPagerBinding

/**
 * @Description:    管理 ViewPager 的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 11:14
 */
class HomeViewPagerFragment :BaseFragment() {
    private lateinit var viewPagerBinding:FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPagerBinding = FragmentViewPagerBinding.inflate(inflater,container,false)
        initView()
        initListener()
        return viewPagerBinding.root
    }


    override fun initView() {
        val pagerAdapter  = ChestnutPagerAdapter(this)
        viewPagerBinding.viewPager.adapter = pagerAdapter

        }

    override fun initListener() {
        //监听底部点击事件
        viewPagerBinding.bottomNavigationMain.setNavigationChangeListener { _, position ->
            viewPagerBinding.viewPager.setCurrentItem(position, true)
        }
    }

}