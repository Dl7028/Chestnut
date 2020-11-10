package com.yks.chestnutyun.data.bean


import java.io.Serializable


/**
 * 用户信息
 */

data class User(
    val id: Int? = null,
    var portrait: String? = null,   //头像
    var nickname: String? = null, //昵称
    val email: String? = null, //邮箱
    val phoneNumber: String? = null,//手机号
    val personalizedSignature:String? = null, //个性签名
    val password:String? = null // 密码
)