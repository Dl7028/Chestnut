package com.yks.chestnutyun.api

import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.data.bean.LoginData
import com.yks.chestnutyun.common.REGISTER_GET_CODE
import com.yks.chestnutyun.common.USER_LOGIN
import com.yks.chestnutyun.common.USER_REGISTER
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @Description:    登录使用的接口调用方法
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 16:01
 */
interface LoginService {

    
    /**
     * 注册接口
     */
    @GET(USER_REGISTER)
    suspend fun register(@Query("username")username: String,
                         @Query("password")password: String,
                         @Query("verificationCode")verificationCode:String): BaseBean<String>

    /**
     * 获取验证码
     */
    @GET(REGISTER_GET_CODE)
    suspend fun getCode(@Query("username") userName:String): BaseBean<String>


    @GET(USER_LOGIN)
    suspend fun login(@Query("username")userName:String,@Query("password")password:String):BaseBean<String>
}