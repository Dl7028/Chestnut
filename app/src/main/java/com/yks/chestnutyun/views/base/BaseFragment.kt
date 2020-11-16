package com.yks.chestnutyun.views.base

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.yks.chestnutyun.customView.ProgressDialogFragment

/**
 * @Description:    Fragment 基类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/30 9:09
 */
abstract class BaseFragment : Fragment(){

    private lateinit var progressDialogFragment: ProgressDialogFragment
    private var isLoaded = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutResId(), container, false)
    }
    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            onFragmentFirstVisible()
            isLoaded = true
        }
    }
    protected open fun onFragmentFirstVisible() {
        initView()
        initData()
        startObserve()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    abstract fun setLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()
    abstract fun startObserve()
    /**
     * 显示加载(转圈)对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {  //对话框未被创建
            progressDialogFragment = ProgressDialogFragment.newInstance() //创建实例
        }


        if (!progressDialogFragment.isAdded) { //fragment 未被加入当前布局
            activity?.supportFragmentManager?.let {
                progressDialogFragment.show(it, message, false)   //加入fragment 显示对话框
            }
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) { //有对话框的实例并且fragment 是可见的
            progressDialogFragment.dismissAllowingStateLoss() //隐藏对话框
        }
    }

}