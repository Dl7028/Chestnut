package com.yks.chestnutyun.viewmodels

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yks.chestnutyun.BR
import com.yks.chestnutyun.data.repositories.RegisterRepository
import com.yks.chestnutyun.utils.RegExpUtils
import com.yks.chestnutyun.utils.ToastUtils
import java.lang.Exception
import java.util.regex.Pattern

/**
 * @Description:    注册功能的ViewModel类
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:24
 */


class RegisterViewModel @ViewModelInject  constructor(
    private val registerRepository: RegisterRepository,
): ViewModel() {
    private companion object

    val TAG: String = "RegisterViewModel"


    val mUserName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mUserPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mVerificationCode: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mConfirmPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mToastString: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val ifProgressShow: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val checkResult: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }




    private var result: Boolean = false



    fun toRegister(name: String,password:String,verificationCode:String): LiveData<Boolean> = liveData {

//            注册返回的结果
        result = registerRepository.register(name, password, verificationCode)
        emit(result)
    }
}




