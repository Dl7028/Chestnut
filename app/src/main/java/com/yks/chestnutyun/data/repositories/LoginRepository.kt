package com.yks.chestnutyun.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.data.network.NetWorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:    登录的仓库类,处理网络返回的数据
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/28 22:36
 */

@Singleton
class LoginRepository  @Inject constructor(){

    /**
     * 用户登录
     */
    suspend fun toLogin(name: String,password:String) : ResultState<String> {

        return withContext(Dispatchers.IO){ //移出主线程
            val baseBean = NetWorkManager.login(name,password) //访问网络获取数据
            if (baseBean.code==1){       //注册成功
                return@withContext ResultState.Success(baseBean.message)
            }else{
                return@withContext ResultState.Error(baseBean.message) //注册失败
            }

        }
    }

}
