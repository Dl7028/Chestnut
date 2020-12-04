package com.yks.chestnutyun.data.repositories.base

import android.util.Log
import com.yks.chestnutyun.data.api.http.RetrofitClient
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.bean.base.BaseBean
import com.yks.chestnutyun.utils.safeApiCall
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 16:33
 */
class RemoteDataSource@Inject constructor() {

    private   val TAG: String="RemoteDataSource"
    private val ApiImpl = RetrofitClient.getInStance().service


    //================================【登录相关】=======================================

    //获取验证码
    suspend fun getCode(userName: String) = safeApiCall(call = {toGetCode(userName)})
    //注册
    suspend fun register(username: String, password: String,verificationCode:String ) = safeApiCall(call = { toRegister(username, password,verificationCode) })
    //登录
    suspend fun login(username: String, password: String) = safeApiCall(call = { toLogin(username,password) })

    /**
     *  网络请求获取验证码
     *
     * @param userName 用户名
     * @return
     */
    private suspend fun toGetCode(userName: String):ResultData<String> {
        val getCodeResult = ApiImpl.getCode(userName)
        if (getCodeResult.code==1){
            return ResultData.Success(getCodeResult.message)
        }
        return ResultData.ErrorMessage(getCodeResult.message)
    }

    /**
     *  网络请求注册
     *
     * @param username 用户名
     * @param password 密码
     * @param verificationCode 验证码
     * @return
     */
    private suspend fun toRegister(username: String, password: String, verificationCode:String): ResultData<String> {
        val registerResult = ApiImpl.register(username, password,verificationCode)
        if (registerResult.code == 1) {
            return ResultData.Success(registerResult.message)
        }
        return ResultData.ErrorMessage(registerResult.message)
    }

    /**
     *  网络请求登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
     suspend fun toLogin(userName:String, password: String):ResultData<String>{
        val loginResult =ApiImpl.login(userName,password)
        if (loginResult.code==1){
            return ResultData.Success(loginResult.message)

        }
        return ResultData.ErrorMessage(loginResult.message)
    }


    //================================【用户相关】=======================================


    //获取用户信息
    suspend fun getUserInfo(name: String) = safeApiCall(call = {toGetUserInfo(name)})
    //修改用户信息
    suspend fun modifyUserMessages(user: User) = safeApiCall(call = {toModifyUserMessages(user)})
    //上传用户头像
    suspend fun postPortrait(part: MultipartBody.Part) = safeApiCall(call = {toPostPortrait(part)})

    /**
     *  获取用户信息
     *
     * @param name 用户名
     * @return
     */
    private suspend fun toGetUserInfo(name: String): ResultData<BaseBean<User>> {
        val getResult =ApiImpl.getUserInfo(name)
        if (getResult.code==1){
            return ResultData.Success(getResult)
        }
        return ResultData.ErrorMessage(getResult.message)
    }

    /**
     *  修改用户信息
     *
     * @param user 用户实例
     * @return
     */
    private suspend fun toModifyUserMessages(user: User):ResultData<String> {
        val changeResult =ApiImpl.modifyUserMessages(user)
        if (changeResult.code==1){
            return ResultData.Success(changeResult.message)
        }
        return ResultData.ErrorMessage(changeResult.message)
    }

    /**
     * 上次用户图片
     *
     * @param portrait
     * @return
     */
    private suspend fun toPostPortrait(part: MultipartBody.Part):ResultData<String>{
        val postResult =ApiImpl.postPortrait(part)
        Timber.d(TAG, ""+postResult)
        if (postResult.code==1){
            return ResultData.Success(postResult.message)
        }
        return ResultData.ErrorMessage(postResult.message)
    }

    //================================【文件相关】=======================================

    suspend fun postFile(part: MultipartBody.Part) = safeApiCall(call = {toPostFile(part)})

    /**
     * 上传文件
     *
     * @param part
     * @return
     */
    private suspend fun toPostFile(part: MultipartBody.Part):ResultData<String>{
        val postFileResult =ApiImpl.postFile(part)
        Timber.d(TAG, ""+postFileResult.message)
        if (postFileResult.code==1){
            return ResultData.Success(postFileResult.message)
        }
        return ResultData.ErrorMessage(postFileResult.message)
    }


}