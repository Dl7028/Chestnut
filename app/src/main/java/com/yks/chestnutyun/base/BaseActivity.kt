package com.yks.chestnutyun.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.TypedValue
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yks.chestnutyun.R
import com.yks.chestnutyun.customView.ProgressDialogFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 11:39
 */

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())

        initActivity(savedInstanceState)
    }


    abstract fun startObserve()

    protected open fun initActivity(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        startObserve()
//        initData()
    }

    abstract fun setLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)

//    abstract fun initData()

    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

}