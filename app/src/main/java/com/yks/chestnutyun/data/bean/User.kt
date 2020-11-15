package com.yks.chestnutyun.data.bean


import java.io.Serializable


/**
 * 用户信息
 */

data class User(
    val email: Any?=null,
    var nickname: String?=null,
    var password: String?=null,
    var personalizedSignature: String?=null,
    val phoneNumber: String?=null,
    var portrait: Any?=null,
    val userId: Int = 0


) {
    override fun toString(): String {
        return "User(email=$email, nickname=$nickname, password=$password, personalizedSignature=$personalizedSignature, phoneNumber=$phoneNumber, portrait=$portrait, userId=$userId)"
    }
}
