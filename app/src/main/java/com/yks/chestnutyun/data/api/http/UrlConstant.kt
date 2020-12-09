package com.yks.chestnutyun.data.api.http

/**
 * @Description:    网址常量
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 20:26
 */

const val BASE_URL = "http://47.102.212.18:50010/"
//==============================【用户相关】============================
//用户注册
const val USER_REGISTER = "user/register"
//注册获取 的验证码
const val REGISTER_GET_CODE = "user/register/getCode"
//用户登录
const val USER_LOGIN = "user/login"
//修改个人信息
const val USER_INFO = "userInfo"
//获取个人信息
const val USER = "user"
//用户上传图片
const val USER_INFO_PORTRAIT = "userInfo/portrait"
//==============================【文件相关】============================

//上传文件
const val FILE_MANAGER_FILE = "file/manager/file"
//获取文件列表
const val FILE_MANAGER_LS = "file/manager/ls"
//预览图片
const val FILE_MANAGER_SHOW_PICTURE = "file/manager/show/picture"
//删除文件
const val FILE_MANAGER_DELETE_FILE = "file/manager/file"

