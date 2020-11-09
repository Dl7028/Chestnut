package com.yks.chestnutyun.views.files.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yks.chestnutyun.R

/**
 * @Description:    图片标签的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/31 10:51
 */
class TabPictureFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_picture_tab,container,false)
    }


}