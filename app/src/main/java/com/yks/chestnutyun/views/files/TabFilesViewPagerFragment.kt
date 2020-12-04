package com.yks.chestnutyun.views.files

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.*
import com.yks.chestnutyun.adaper.VIDEO_PAGE_INDEX
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentTabViewPagerBinding
import com.yks.chestnutyun.utils.ACCEPTED_ALL
import com.yks.chestnutyun.utils.GetContentWithMimeTypes
import kotlinx.android.synthetic.main.fragment_tab_view_pager.*
import timber.log.Timber

/**
 * @Description:    管理 ViewPager 的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 11:14
 */
class TabFilesViewPagerFragment : BaseFragment() {
    private lateinit var viewPagerBinding: FragmentTabViewPagerBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPagerBinding = FragmentTabViewPagerBinding.inflate(inflater,container,false)
        initView()
        return viewPagerBinding.root

    }

    override fun setLayoutResId(): Int  = R.layout.fragment_tab_view_pager

    override fun initView() {
        val pagerAdapter = ChestnutPagerAdapter(this)
        val tabLayout = viewPagerBinding.tabs
        val viewPager = viewPagerBinding.viewPager
        viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->     //设置tabLayout
            tab.text = getTabTitle(position)
        }.attach()
        viewPagerBinding.selectDocumentButton.setOnClickListener{
            selectDocument.launch(ACCEPTED_ALL)
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    private val selectDocument = registerForActivityResult(GetContentWithMimeTypes()){
        Timber.d("getContent获取到的uri为：${it.toString()}")
    }



    //获取tabLayout对应位置的标题
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            ALL_FILES_PAGE_INDEX -> getString(R.string.all_files)
            VIDEO_PAGE_INDEX ->getString(R.string.video)
            MUSIC_PAGE_INDEX ->getString(R.string.music)
            PICTURE_PAGE_INDEX ->getString(R.string.picture)
            else -> null
        }
    }


}