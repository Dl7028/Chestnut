package com.yks.chestnutyun.data.network

import androidx.lifecycle.LiveData
import com.yks.chestnutyun.api.LoginService
import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.data.bean.LoginData
import com.yks.chestnutyun.utils.ServiceCreator
import java.lang.Exception

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 15:59
 */
object NetWorkManager {

    private val loginImpl = ServiceCreator.create(LoginService::class.java)


    suspend fun register(username:String,password:String,verificationCode:String):  LiveData<BaseBean<LoginData>> = loginImpl.register(username, password,password)


}