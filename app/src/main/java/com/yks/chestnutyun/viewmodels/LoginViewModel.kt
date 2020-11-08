package com.yks.chestnutyun.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yks.chestnutyun.base.BaseBean
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.data.bean.LoginData
import com.yks.chestnutyun.data.network.NetWorkManager
import com.yks.chestnutyun.data.repositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * @Description:    登录功能的ViewModel
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/28 22:34
 */
class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(){

     val loginResult = MutableLiveData<ResultState<String>>()



    /**
     * 登录
     */
    fun toLogin(name: String,password:String){
        viewModelScope.launch(Dispatchers.IO){
            val result =  try {
                loginRepository.toLogin(name,password)
            }catch (e: Exception){
                ResultState.Error(e.message.toString())
            }
            loginResult.postValue(result)
        }
    }







}