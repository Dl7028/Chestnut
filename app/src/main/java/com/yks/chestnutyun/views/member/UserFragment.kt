package com.yks.chestnutyun.views.member

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.R
import com.yks.chestnutyun.databinding.FragmentUserBinding
import com.yks.chestnutyun.utils.ToastUtils
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * @Description:    用户中心的fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/11/9 15:25
 */
@AndroidEntryPoint
class UserFragment: BaseFragment() {

    private val TAG: String? = "UserFragment"
    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //初始化binding
        binding = DataBindingUtil.inflate<FragmentUserBinding>(
            inflater,
            setLayoutResId(),
            container,
            false
        )
        binding.apply {
            viewModel =viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun setLayoutResId(): Int = R.layout.fragment_user




    override fun initView() {
        val bundle = requireActivity().intent.extras!!
        val name = bundle.getString("username")!!
        viewModel.getUserInfo(name)





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
        viewModel.mGetUserInfoResultStatus.observe(this){
//            if (it.showLoading) showProgressDialog(R.string.login_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                binding.apply {
                    userNickName.text = it.data?.nickname
                    userPhoneNumber.text = it.data?.phoneNumber
                    userEmilAddress.text = it.data?.email.toString()
                    userPersonalizedSignature.text = it.data?.personalizedSignature
                }
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtils.showToast(activity,""+it.showError)
            }
        }
    }
}