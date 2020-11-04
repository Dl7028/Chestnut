package com.yks.chestnutyun.viewmodels

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.yks.chestnutyun.data.repositories.RegisterRepository

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */


class RegisterViewModel @ViewModelInject  constructor(
     registerRepository:RegisterRepository,
): ViewModel(){
    private companion object val TAG: String = "RegisterViewModel"


     val mUserName = MutableLiveData<String>(" ")
     val mUserPassword =  MutableLiveData<String>(" ")
     val mVerificationCode = MutableLiveData<String>(" ")
     val mConfirmPassword =  MutableLiveData<String>(" ")











    val result = registerRepository.register("name", "password","verificationCode")


    val toRegisters:LiveData<Boolean> = liveData{

        val name = mUserName.value!!
        val password =mUserPassword.value!!
        val verificationCode = mVerificationCode.value!!
        val confirmPassword = mConfirmPassword.value!!
        Log.d(TAG, "y用户名          - $mUserName.value!!"+mUserPassword.value!!+mVerificationCode.value!!+mConfirmPassword.value!!)

        val ifSuccessToRegister =  registerRepository.register("name", "password","verificationCode")
//        emit(ifSuccessToRegister.value!!)

    }





    //检查注册信息
    private fun checkMessage(username:String,password:String,verificationCode:Int,confirmPassword:String): Boolean{

        //判断是否是手机号
        //判断是否是邮箱号
        //判断两次密码是否相同
        return true
    }

}