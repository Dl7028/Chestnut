package com.yks.chestnutyun.data.repositories.base

import android.util.Log
import com.yks.chestnutyun.api.LoginService
import com.yks.chestnutyun.data.base.ResultData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.network.NetWorkManager
import com.yks.chestnutyun.data.network.ServiceCreator
import com.yks.chestnutyun.utils.safeApiCall

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 16:33
 */
object RemoteDataSource {

    private  const val TAG: String="RemoteDataSource"
    private val loginImpl = ServiceCreator.create(LoginService::class.java)

    //注册
    suspend fun register(username: String, password: String,verificationCode:String ) = safeApiCall(
        call = { toRegister(username, password,verificationCode) }
    )

    /**
     * 网络请求注册
     */
    private suspend fun toRegister(
        username: String,
        password: String,
        verificationCode:String
    ): ResultData<User> {
        val register = loginImpl.register(username, password,verificationCode)
        Log.d(TAG,register.toString())
        if (register.code == 0) {
            return ResultData.Success(register.data)
        }
        return ResultData.Error(Exception(register.message))
    }

}