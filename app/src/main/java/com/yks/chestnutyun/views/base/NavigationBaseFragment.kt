package com.yks.chestnutyun.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/16 22:18
 */
abstract class NavigationBaseFragment : BaseFragment() {
    var isFragmentViewInit = false
    var lastView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (lastView == null) {
            lastView = setRootView(inflater, container, savedInstanceState)
        }
        return lastView
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        if (!isFragmentViewInit) {
            super.onViewCreated(view, savedInstanceState)
            initView(view)
            initData()
            isFragmentViewInit = true
        }
    }

    /**
     * View初始化
     *
     * @param view 布局
     */
    abstract fun initView(view: View?)

    /**
     * 初始化数据
     */
    abstract override fun initData()

    /**
     * 设置根view
     *
     * @param inflater           1
     * @param container          2
     * @param savedInstanceState 3
     * @return
     */
    abstract fun setRootView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View?
}
