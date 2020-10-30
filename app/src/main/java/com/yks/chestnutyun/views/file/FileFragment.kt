package com.yks.chestnutyun.views.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yks.chestnutyun.R
import com.yks.chestnutyun.base.BaseFragment

/**
 * @Description:    文件fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 23:03
 */
class FileFragment : BaseFragment(){






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file,container,false)
    }


    override fun initView() {
        TODO("Not yet implemented")
    }



    override fun initListener() {
        TODO("Not yet implemented")
    }
}