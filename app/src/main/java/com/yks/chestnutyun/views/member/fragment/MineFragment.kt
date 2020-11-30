package com.yks.chestnutyun.views.member.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yks.chestnutyun.R
import com.yks.chestnutyun.ext.setOnClickWithFilter
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.utils.ToastUtil
import com.yks.chestnutyun.viewmodels.UserViewModel
import com.yks.chestnutyun.views.member.activity.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @Description:    我的
 * fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 23:03
 */
@AndroidEntryPoint
class MineFragment: BaseFragment() {


    private val viewModel: UserViewModel by viewModels()
    companion object val TAG = "MineFragment"



    override fun setLayoutResId(): Int = R.layout.fragment_mine

    override fun initView() {
        val bundle = requireActivity().intent.extras!!
        mineGoUserButton.setOnClickWithFilter {
            //获取用户信息
            val intent = Intent()
            intent.putExtra("username", bundle.getString("username")!!)
            intent.setClass(requireActivity(), UserActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }

    //更新数据
    override fun onResume() {
        super.onResume()
        val bundle = requireActivity().intent.extras!!
        viewModel.getUserInfo(bundle.getString("username")!!)
    }

    override fun initData() {
    }

    override fun startObserve() {
        viewModel.mGetUserInfoResultStatus.observe(this){
//            if (it.showLoading) showProgressDialog(R.string.save_loading) else dismissProgressDialog()  //显示/隐藏 进度条
            if (it.showEnd) {
                titleName.text = it.data?.nickname
                if (it.data?.phoneNumber!=null) titleUserName.text = it.data.phoneNumber else  titleUserName.text = it.data?.email
                if (it.data?.portrait!=null) Glide.with(this).load(it.data.portrait).into(minePicture)
            }
            it.showError?.let { errorMsg ->        //请求失败
                ToastUtil.showToast(it.showError)
            }
        }
    }
}








