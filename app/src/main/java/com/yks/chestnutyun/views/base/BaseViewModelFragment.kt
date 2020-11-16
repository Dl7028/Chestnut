package com.yks.chestnutyun.views.base

import com.yks.chestnutyun.viewmodels.base.BaseViewModel

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/16 21:15
 */
abstract  class BaseViewModelFragment<VM: BaseViewModel>:BaseFragment() {
    lateinit var mViewModel:VM

    override fun onFragmentFirstVisible() {
        mViewModel = initVM()
        startObserve()
        super.onFragmentFirstVisible()
    }


    abstract fun initVM(): VM

}