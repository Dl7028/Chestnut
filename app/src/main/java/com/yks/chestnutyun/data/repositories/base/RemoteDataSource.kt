package com.yks.chestnutyun.data.repositories.base

import com.yks.chestnutyun.data.api.LoginService
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.api.ServiceCreator
import com.yks.chestnutyun.utils.safeApiCall
import javax.inject.Inject

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 16:33
 */
class RemoteDataSource@Inject constructor() {

    private   val TAG: String="RemoteDataSource"
    private val loginImpl = ServiceCreator.create(LoginService::class.java)

    //注册
    suspend fun register(username: String, password: String,verificationCode:String ) = safeApiCall(
        call = { toRegister(username, password,verificationCode) }
    )

    //登录
    suspend fun login(username: String, password: String) = safeApiCall(
        call = { toLogin(username,password) }
    )
    /**
     * 网络请求注册
     */
    private suspend fun toRegister(
        username: String,
        password: String,
        verificationCode:String
    ): ResultData<String> {
        val registerResult = loginImpl.register(username, password,verificationCode)
        if (registerResult.code == 1) {
            return ResultData.Success(registerResult.message)
        }
        return ResultData.ErrorMessage(registerResult.message)
    }

    /**
     * 网络请求登录
     */
     suspend fun toLogin(
        userName:String,
        password: String
    ):ResultData<String>{
        val loginResult =loginImpl.login(userName,password)
        if (loginResult.code==1){
            return ResultData.Success(loginResult.message)

        }
        return ResultData.ErrorMessage(loginResult.message)
    }

}