package com.yks.chestnutyun.data.network

import com.yks.chestnutyun.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Description:    Retrofit 创建 封装
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/2 15:54
 */


object ServiceCreator {


    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)




    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}