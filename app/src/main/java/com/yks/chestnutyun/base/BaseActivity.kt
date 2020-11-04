package com.yks.chestnutyun.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 11:39
 */

@AndroidEntryPoint
 open  class BaseActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

}