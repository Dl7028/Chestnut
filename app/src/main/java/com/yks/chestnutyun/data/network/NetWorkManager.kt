package com.yks.chestnutyun.data.network

import com.yks.chestnutyun.api.LoginService
import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.data.bean.LoginData

/**
 * @Description:    管理接口请求的类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 15:59
 */
object NetWorkManager {

    private val loginImpl = ServiceCreator.create(LoginService::class.java)


    suspend fun register(username:String,password:String,verificationCode:String):  BaseBean<LoginData>
            = loginImpl.register(username, password,verificationCode)

    suspend fun getCode(userName:String):BaseBean<String> = loginImpl.getCode(userName)


}