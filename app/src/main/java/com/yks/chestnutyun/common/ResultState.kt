package com.yks.chestnutyun.common

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/7 22:04
 */
sealed class ResultState<out R> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()
}