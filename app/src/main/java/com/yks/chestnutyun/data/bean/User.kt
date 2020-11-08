package com.yks.chestnutyun.data.bean


import java.io.Serializable


/**
 * 用户信息
 */
data class User(
    val id: Int = 0,
    var icon: String? = null,
    var publicName: String? = null,
    val username: String? = null
) : Serializable