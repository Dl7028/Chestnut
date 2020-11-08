package com.yks.chestnutyun.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

import com.yks.chestnutyun.data.base.ResultData
import kotlinx.coroutines.flow.Flow


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



