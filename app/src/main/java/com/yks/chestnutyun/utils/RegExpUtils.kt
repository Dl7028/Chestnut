package com.yks.chestnutyun.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/5 21:58
 */
object RegExpUtils {


    /**
     *
     * 检查邮箱
     * @param email
     * @return
     */
    fun checkEmail(email: String?): Boolean {
        val regExp =
            "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
        val p = Pattern.compile(regExp)
        val m: Matcher = p.matcher(email)
        return m.matches()
    }

    /**
     * 验证手机号
     */
    fun checkPhone(phone: String?): Boolean {
        val regExp = "^1[0-9]{10}"
        val p = Pattern.compile(regExp)
        val m: Matcher = p.matcher(phone)
        return m.matches()
    }

}