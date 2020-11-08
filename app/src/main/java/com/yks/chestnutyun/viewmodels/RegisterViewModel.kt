package com.yks.chestnutyun.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yks.chestnutyun.common.ResultState
import com.yks.chestnutyun.data.repositories.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.E

/**
 * @Description:    注册功能的ViewModel类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */


class RegisterViewModel @ViewModelInject  constructor(
    private val registerRepository: RegisterRepository,
): ViewModel()  {
    private companion object val TAG: String = "RegisterViewModel"


    val registerResult = MutableLiveData<ResultState<String>>()


    /**
     * 注册
     */
    fun toRegister(name: String,password:String,verificationCode:String){
        viewModelScope.launch(Dispatchers.IO){
          val result =  try {
              registerRepository.toRegister(name,password,verificationCode)
          }catch (e: Exception){
              ResultState.Error(e.message.toString())
          }
            registerResult.postValue(result)
        }
    }

    /**
     * 获取验证码
     */
    fun getCode(userName:String):LiveData<Boolean> = liveData{
        val code = registerRepository.getCode(userName)
        emit(code)
    }
}




