package com.yks.chestnutyun.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yks.chestnutyun.data.repositories.RegisterRepository
import com.yks.chestnutyun.utils.ListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * @Description:    注册功能的ViewModel类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */


class RegisterViewModel @ViewModelInject  constructor(
    private val registerRepository: RegisterRepository,
): ViewModel()  {
    private companion object val TAG: String = "RegisterViewModel"


//    val registerResult = MutableLiveData<ResultState<String>>()
    val mRegisterStatus = MutableLiveData<ListModel<Int>>()
    val mLoginStatus = MutableLiveData<ListModel<Int>>()


    /**
     * 获取验证码
     */
    fun getCode(userName:String):LiveData<Boolean> = liveData{
        val code = registerRepository.getCode(userName)
        emit(code)
    }

    /**
     * 注册
     */
    fun register(username: String, password: String, verificationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.register(
                    username,
                    password,
                    verificationCode,
                    mRegisterStatus      //网络请求的状态量
                )
            }
        }

    /**
     * 登录
     */
    fun login(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.login(
                username,
                password,
                mLoginStatus
            )
        }
    }
}







