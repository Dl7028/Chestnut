package com.yks.chestnutyun.views.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.R
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * @Description:    用户中心的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:25
 */
@AndroidEntryPoint
class UserFragment: BaseFragment() {

    private val viewModel: UserViewModel by viewModels()


    override fun setLayoutResId(): Int = R.layout.fragment_user


    override fun initView() {
        user_nick_name.setOnClickListener{
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_nickname_fragment)
        }
       userPersonalizedSignature.setOnClickListener{
            findNavController().navigate(R.id.action_nav_user_center_fragment_to_nav_user_change_sign_nature_fragment)
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}