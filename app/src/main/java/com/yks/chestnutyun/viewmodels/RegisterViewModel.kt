package com.yks.chestnutyun.viewmodels

import android.opengl.Visibility
import android.util.Log
import android.widget.ProgressBar
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yks.chestnutyun.data.repositories.RegisterRepository
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


    val mUserName = MutableLiveData<String>()
    val mUserPassword = MutableLiveData<String>()
    val mVerificationCode = MutableLiveData<String>()
    val mConfirmPassword = MutableLiveData<String>()
    val mToastString = MutableLiveData<String>()
    val ifProgressShow = MutableLiveData<Boolean>(false)

    private var result:LiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val patternMailBox: Pattern =
        Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\\\.][A-Za-z]{2,3}([\\\\.][A-Za-z]{2})?\$") // 正则表达的式匹配邮箱
    private val patternTell: Pattern = Pattern.compile("^1[0-9]{10}") // 正则表达式匹配手机号





    fun toRegister():LiveData<Boolean>{

        val username = mUserName.value.toString()
        val password = mUserPassword.value.toString()
        val confirmPassword = mConfirmPassword.value.toString()
        val verificationCode = mVerificationCode.value.toString()

        val checkResult =  checkMessage(username,password,confirmPassword,verificationCode)

        if (checkResult){
            ifProgressShow.postValue(true)
//            注册返回的结果
            result = registerRepository.register(username, password, verificationCode)
            return result
        }
        return  result
    }





    //检查注册信息
    private fun checkMessage(
        username: String,
        password: String,
        verificationCode: String,
        confirmPassword: String
    ): Boolean{
        //判断是否是手机号或者邮箱号

        if (!patternMailBox.matcher(username).matches()&&!patternTell.matcher(username).matches()){
            Log.d("","")
            throw Exception("用户名格式不合法")

        }
        if (verificationCode.isEmpty()) {
            Log.d("checkMessage","验证码不为空")
            throw Exception("验证码不能为空")

        }
        if(password == confirmPassword){         //判断两次密码是否相同
            Log.d("checkMessage","两次密码匹配")

            throw Exception("两次密码不匹配")
        }
        return true
    }


    fun setToast(toastString:String){
        mToastString.postValue(toastString)
    }



}