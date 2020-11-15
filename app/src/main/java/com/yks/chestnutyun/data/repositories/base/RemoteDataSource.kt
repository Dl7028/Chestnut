package com.yks.chestnutyun.data.repositories.base

import com.yks.chestnutyun.data.api.ApiService
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.api.ServiceCreator
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.utils.safeApiCall
import javax.inject.Inject

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 16:33
 */
class RemoteDataSource@Inject constructor() {

    private   val TAG: String="RemoteDataSource"
    private val ApiImpl = ServiceCreator.create(ApiService::class.java)


    //============【登录相关】===========

    //获取验证码
    suspend fun getCode(userName: String) = safeApiCall(
        call = {toGetCode(userName)}
    )

    //注册
    suspend fun register(username: String, password: String,verificationCode:String ) = safeApiCall(
        call = { toRegister(username, password,verificationCode) }
    )

    //登录
    suspend fun login(username: String, password: String) = safeApiCall(
        call = { toLogin(username,password) }
    )


    /**
     * 网络请求获取验证码
     */
    private suspend fun toGetCode(userName: String):ResultData<String> {
        val getCodeResult = ApiImpl.getCode(userName)
        if (getCodeResult.code==1){
            return ResultData.Success(getCodeResult.message)
        }
        return ResultData.ErrorMessage(getCodeResult.message)
    }

    /**
     * 网络请求注册
     */
    private suspend fun toRegister(username: String, password: String, verificationCode:String): ResultData<String> {
        val registerResult = ApiImpl.register(username, password,verificationCode)
        if (registerResult.code == 1) {
            return ResultData.Success(registerResult.message)
        }
        return ResultData.ErrorMessage(registerResult.message)
    }

    /**
     * 网络请求登录
     */
     suspend fun toLogin(userName:String, password: String):ResultData<String>{
        val loginResult =ApiImpl.login(userName,password)
        if (loginResult.code==1){
            return ResultData.Success(loginResult.message)

        }
        return ResultData.ErrorMessage(loginResult.message)
    }


    //===============【用户相关】======================

    //修改用户信息
    suspend fun modifyUserMessages(user: User) = safeApiCall(
        call = {toModifyUserMessages(user)}
    )

    /**
     * TODO 修改用户信息
     *
     * @param user
     * @return
     */
    private suspend fun toModifyUserMessages(user: User):ResultData<User> {
        val changeResult =ApiImpl.modifyUserMessages(user)
        if (changeResult.code==1){
            return ResultData.Success(changeResult.data)
        }
        return ResultData.ErrorMessage(changeResult.message)
    }


}