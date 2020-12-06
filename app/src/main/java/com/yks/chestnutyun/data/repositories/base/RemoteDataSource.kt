package com.yks.chestnutyun.data.repositories.base

import com.yks.chestnutyun.data.api.http.RetrofitClient
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.data.bean.base.ResultData
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.data.bean.base.BaseBean
import com.yks.chestnutyun.utils.safeApiCall
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

/**
 * @Description:    数据源
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/8 16:33
 */
class RemoteDataSource@Inject constructor() {

    private   val TAG: String="RemoteDataSource"
    private val apiImpl = RetrofitClient.getInStance().service


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
        val getCodeResult = apiImpl.getCode(userName)
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
        val registerResult = apiImpl.register(username, password,verificationCode)
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
        val loginResult =apiImpl.login(userName,password)
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
        val getResult =apiImpl.getUserInfo(name)
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
        val changeResult =apiImpl.modifyUserMessages(user)
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
        val postResult =apiImpl.postPortrait(part)
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
        val postFileResult =apiImpl.postFile(part)
        Timber.d(TAG, ""+postFileResult.message)
        if (postFileResult.code==1){
            return ResultData.Success(postFileResult.message)
        }
        return ResultData.ErrorMessage(postFileResult.message)
    }

    suspend fun getFileList() = safeApiCall(call = {toGetFileList()})

    /**
     * 获取文件列表
     *
     * @return
     */
    private suspend fun toGetFileList():ResultData<MutableList<FileItem>>{
        val getResult =apiImpl.getFileList()
        if (getResult.code==1){
            return ResultData.Success(getResult.data)
        }
        return ResultData.ErrorMessage(getResult.message)
    }


}