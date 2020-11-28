
package com.yks.chestnutyun.utils


data class ListModel<T>(

    val data:T ?= null,
    val showSuccess: List<T>? = null,
    val showLoading: Boolean = false,
    val showError: String? = null,
    val showEnd: Boolean = false, // 加载更多
    val isRefresh: Boolean = false, // 刷新
    val isRefreshSuccess: Boolean = true, // 是否刷新成功
    val needLogin: Boolean? = false,
//    val loadPageStatus: LiveData<LoadPageStatus>? = null
)