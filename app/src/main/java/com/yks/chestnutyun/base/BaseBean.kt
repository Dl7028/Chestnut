package com.yks.chestnutyun.base

/**
 * @Description:    默认返回的bean类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 16:05
 */
data class BaseBean<T>(
    val data: T,
    val code: Int = 0,
    val message: String = ""


)
