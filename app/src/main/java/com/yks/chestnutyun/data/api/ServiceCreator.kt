package com.yks.chestnutyun.data.api

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


    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .addInterceptor(ReceivedCookiesInterceptor())
        .addInterceptor(AddCookiesInterceptor())
        .build()





    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}