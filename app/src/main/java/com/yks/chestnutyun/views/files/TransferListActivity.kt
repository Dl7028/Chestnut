package com.yks.chestnutyun.views.files

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.yks.chestnutyun.R
import com.yks.chestnutyun.adaper.ChestnutPagerAdapter
import com.yks.chestnutyun.adaper.DOWNLOAD_LIST
import com.yks.chestnutyun.adaper.TransferListAdapter
import com.yks.chestnutyun.adaper.UPLOAD_LIST
import com.yks.chestnutyun.utils.ACCEPTED_DOCUMENT
import com.yks.chestnutyun.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_transfer_list.*
import kotlinx.android.synthetic.main.activity_transfer_list.tabs
import kotlinx.android.synthetic.main.fragment_tab_view_pager.*

class TransferListActivity : BaseActivity() {



    override fun setLayoutId(): Int = R.layout.activity_transfer_list

    override fun initView(savedInstanceState: Bundle?) {
        val pagerAdapter = TransferListAdapter(this)
        val tabLayout = tabs
        val viewPager = viewPager
        viewPager.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->     //设置tabLayout
            tab.text = getTabTitle(position)
        }.attach()

    }

    override fun startObserve() {

    }

    //获取tabLayout对应位置的标题
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            UPLOAD_LIST -> "上传列表"
            DOWNLOAD_LIST ->"下载列表"
            else -> null
        }
    }

}