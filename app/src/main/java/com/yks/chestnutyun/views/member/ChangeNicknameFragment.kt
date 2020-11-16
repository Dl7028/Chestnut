package com.yks.chestnutyun.views.member

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.R
import com.yks.chestnutyun.data.bean.User
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.views.base.NavigationBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_change_nickname.*

/**
 * @Description:    修改 昵称的Fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/10 10:21
 */

@AndroidEntryPoint
class ChangeNicknameFragment : BaseFragment() {

    private val TAG: String? = "ChangeNicknameFragment"
    private val viewModel: UserViewModel by viewModels()

    override fun setLayoutResId(): Int  = R.layout.fragment_change_nickname



    override fun initView() {
        cancelBackBtn.setOnClickListener{
            //回退
            findNavController().navigateUp()
        }
        saveMessageTv.setOnClickListener{
            //修改信息
            modifyUserMessages()
        }
    }

    override fun initData() {

    }

    override fun startObserve() {
        viewModel.mModifyResultStatus.observe(this){
            if (it.showLoading) showProgressDialog(R.string.save_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                ToastUtils.showToast(activity, "" + it.showEnd)  //请求成功
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity, "" + it.showError)
            }
        }
    }

    private fun modifyUserMessages(){
        val nickname = modifyNicknameEdt.text.toString()
        val user = User()
        user.nickname = nickname
        viewModel.modifyUserMessages(user)
    }

}