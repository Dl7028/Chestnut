package com.yks.chestnutyun.utils

import com.yks.chestnutyun.data.bean.base.ResultData


/**
 * 网络请求
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> ResultData<T>): ResultData<T> {
    return try {
        call()
    } catch (e: Exception) {
        ResultData.Error(e)
    }
}
/**
 * 判断网络状态是否可用
 */



