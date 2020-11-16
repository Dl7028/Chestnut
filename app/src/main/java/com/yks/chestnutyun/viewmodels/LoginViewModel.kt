package com.yks.chestnutyun.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yks.chestnutyun.data.repositories.LoginRepository
import com.yks.chestnutyun.utils.ListModel
import com.yks.chestnutyun.viewmodels.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Description:    注册功能的ViewModel类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */


class LoginViewModel @ViewModelInject  constructor(
    private val loginRepository: LoginRepository,
): BaseViewModel()  {
    private companion object val TAG: String = "RegisterViewModel"


//    val registerResult = MutableLiveData<ResultState<String>>()
    val mRegisterStatus = MutableLiveData<ListModel<Int>>()
    val mLoginStatus = MutableLiveData<ListModel<Int>>()
    val mGetCodeStatus = MutableLiveData<ListModel<Int>>()




    /**
     * 获取验证码
     */
    fun getCode(userName: String){
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.getCode(
                userName,
                mGetCodeStatus
            )
        }
    }


    /**
     * 注册
     */
    fun register(username: String, password: String, verificationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.register(
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
            loginRepository.login(
                username,
                password,
                mLoginStatus
            )
        }
    }
}







