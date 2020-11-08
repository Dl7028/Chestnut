package com.yks.chestnutyun.data.api

import com.yks.chestnutyun.base.BaseBean

/**
 * @Description:    管理接口请求的类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 15:59
 */
object NetWorkManager {

    private val loginImpl = ServiceCreator.create(LoginService::class.java)

/*

    //注册
    suspend fun register(username:String,password:String,verificationCode:String): BaseBean<String>
            = loginImpl.register(username, password,verificationCode)
*/

    //获取验证码
    suspend fun getCode(userName:String):BaseBean<String> = loginImpl.getCode(userName)

    //用户登录
    suspend fun login(userName:String,password:String) = loginImpl.login(userName,password)


}