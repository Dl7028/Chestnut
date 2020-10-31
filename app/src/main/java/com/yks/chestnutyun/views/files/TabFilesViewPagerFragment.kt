package com.yks.chestnutyun.views.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.*
import com.yks.chestnutyun.adaper.VIDEO_PAGE_INDEX
import com.yks.chestnutyun.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentTabViewPagerBinding
import kotlinx.android.synthetic.main.fragment_tab_view_pager.*

/**
 * @Description:    管理 ViewPager 的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 11:14
 */
class TabFilesViewPagerFragment :Fragment() {
    private lateinit var viewPagerBinding: FragmentTabViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPagerBinding = FragmentTabViewPagerBinding.inflate(inflater,container,false)
        val pagerAdapter = ChestnutPagerAdapter(this)
        val tabLayout = viewPagerBinding.tabs
        val viewPager = viewPagerBinding.viewPager
        viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->     //设置tabLayout
            tab.text = getTabTitle(position)
        }.attach()


        return viewPagerBinding.root

    }



    //获取tabLayout对应位置的标题
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            ALL_FILES_PAGE_INDEX -> "全部"
            VIDEO_PAGE_INDEX ->"视频"
            MUSIC_PAGE_INDEX ->"音乐"
            PICTURE_PAGE_INDEX ->"图片"

            else -> null
        }
    }


}