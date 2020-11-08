package com.yks.chestnutyun.data.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yks.chestnutyun.data.base.ResultData
import com.yks.chestnutyun.data.network.NetWorkManager
import com.yks.chestnutyun.data.repositories.base.RemoteDataSource
import com.yks.chestnutyun.utils.ListModel
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
class RegisterRepository @Inject constructor() {

    private val TAG: String? = "RegisterRepository"


    //获取注册的验证码
    suspend fun getCode(userName: String): Boolean {
        val baseBean = NetWorkManager.getCode(userName)
        return baseBean.code == 1
    }

    /**
     * 注册
     */
    /*  suspend fun toRegister(name: String,password:String,verificationCode:String) : ResultState<String> {

        return withContext(Dispatchers.IO){ //移出主线程
            val baseBean = NetWorkManager.register(name,password,verificationCode) //访问网络获取数据
            if (baseBean.code==1){       //注册成功
                return@withContext ResultState.Success(baseBean.message)
            }else{
                return@withContext ResultState.Error(baseBean.message) //注册失败
            }

        }
    }*/

    suspend fun register(
        username: String,
        password: String,
        verificationCode: String,
        listModel: MutableLiveData<ListModel<Int>>?
    ) {
        listModel?.postValue(ListModel(showLoading = true))
        val result = RemoteDataSource.register(username, password, verificationCode)
        Log.d(TAG,result.toString())
        if (result is ResultData.Success) {   //注册成功
//            setLoggedInUser(result.data)
            Log.d(TAG,"result is ResultData.Success")
            listModel?.postValue(ListModel(showLoading = false, showEnd = true))
        } else if (result is ResultData.ErrorMessage) { //注册失败
            listModel?.postValue(ListModel(showLoading = false, showError = result.message))
            Log.d(TAG,"result is ResultData.ErrorMessage")
        }else if (result is ResultData.Error){
            listModel?.postValue(ListModel(showLoading = false, showError = "注册异常"))
        }


    }
}



