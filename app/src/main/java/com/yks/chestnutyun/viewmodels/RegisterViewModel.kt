package com.yks.chestnutyun.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.yks.chestnutyun.data.repositories.LoginRepository
import com.yks.chestnutyun.data.repositories.RegisterRepository

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */

class RegisterViewModel @ViewModelInject constructor(
    private val registerRepository:RegisterRepository,
){


    fun toRegisters(username:String, password:String,verificationCode:String) :LiveData<Boolean> = liveData{
            registerRepository.register(username, password,verificationCode)
    }

}