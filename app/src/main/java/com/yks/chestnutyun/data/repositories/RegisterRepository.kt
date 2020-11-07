package com.yks.chestnutyun.data.repositories

import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.data.network.NetWorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */

@Singleton
class RegisterRepository @Inject constructor(){

    //获取登录请求返回的数据并判断是否注册成功
    suspend fun register(username:String,password:String,verificationCode:String): BaseBean<String>  { //返回一个布尔值的liveData对象

        return NetWorkManager.register(username, password, verificationCode)
            }

    //获取注册的验证码
    suspend fun getCode(userName:String): Boolean{
        val baseBean = NetWorkManager.getCode(userName)
        if (baseBean.code==1){
            return true
        }else{
            throw Exception(baseBean.message)
        }
    }

    /**
     * 注册
     */
    suspend fun toRegister(name: String,password:String,verificationCode:String) : ResultState<Boolean> {

        return withContext(Dispatchers.IO){ //移出主线程
            val baseBean = NetWorkManager.register(name,password,verificationCode) //访问网络获取数据
            if (baseBean.code==1){       //注册成功
                return@withContext ResultState.Success(true)
            }else{
                return@withContext ResultState.Error(baseBean.message) //注册失败
            }

        }
    }
}



