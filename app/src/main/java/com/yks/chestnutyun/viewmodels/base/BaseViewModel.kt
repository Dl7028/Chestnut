package com.yks.chestnutyun.viewmodels.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description:   协程处理网络请求回调
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/16 21:17
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    /**
     * TODO     运行在UI线程的协程 viewModelScope 已经实现了在onCleared取消协程
     *
     * @param block
     */

    fun launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }

}