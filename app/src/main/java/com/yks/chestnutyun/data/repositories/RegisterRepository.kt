package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yks.chestnutyun.data.network.NetWorkManager
import javax.inject.Singleton

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */
@Singleton
class RegisterRepository {

    //获取登录请求返回的数据并判断是否注册成功
    suspend fun register(username:String,password:String,verificationCode:String): LiveData<Boolean> { //返回一个布尔值的liveData对象

        return liveData {
            val baseBean = NetWorkManager.register(username, password, verificationCode)
            baseBean.value?.code == 0
            if (baseBean.value?.code == 0) {
                //注册成功
                emit(true)
            } else {
                //登录失败
                emit(false)
                throw Exception(baseBean.value?.msg)
            }
        }
    }

}