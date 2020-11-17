package com.yks.chestnutyun.data.api

import com.yks.chestnutyun.data.api.http.*
import com.yks.chestnutyun.data.bean.base.BaseBean
import com.yks.chestnutyun.data.bean.User
import retrofit2.http.*

/**
 * @Description:    登录使用的接口调用方法
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 16:01
 */
interface ApiService {


    //================================【注册相关】=======================================
    /**
     * 注册接口
     */
    @GET(USER_REGISTER)
    suspend fun register(@Query("username")username: String,
                         @Query("password")password: String,
                         @Query("verificationCode")verificationCode:String): BaseBean<User>

    /**
     * 获取验证码
     */
    @GET(REGISTER_GET_CODE)
    suspend fun getCode(@Query("username") userName:String): BaseBean<String>


    /**
     * 登录
     */
    @GET(USER_LOGIN)
    suspend fun login(@Query("username")userName:String,@Query("password")password:String): BaseBean<String>


    //================================【用户相关】=======================================
    /**
     * 修改用户信息
     *
     * @param user 用户实例
     * @return
     */
    @PUT(USER_INFO)
    suspend fun modifyUserMessages(@Body user:User):BaseBean<User>

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET(USER)
    suspend fun getUserInfo(@Query("username")name: String):BaseBean<User>

    /**
     *  用户上传 头像
     *
     * @param portrait
     * @return
     */
    @POST(USER_INFO_PORTRAIT)
    suspend fun postPortrait(@Query ("portrait") portrait:String):BaseBean<String>
}