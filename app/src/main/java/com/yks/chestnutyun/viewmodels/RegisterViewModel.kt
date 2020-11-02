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
    @Assisted private val username : String,
    @Assisted private val password: String,
    @Assisted private val verificationCode : String
){

    val registerResult: LiveData<Boolean> = liveData{
        registerRepository.register(username, password,verificationCode)
    }
}