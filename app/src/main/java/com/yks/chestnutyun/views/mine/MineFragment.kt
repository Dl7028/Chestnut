package com.yks.chestnutyun.views.mine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
import com.yks.chestnutyun.R
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentMineBinding

/**
 * @Description:    我的
 * fragment
 * @Author:         Yu ki-r
 * @CreateDate:     2020/10/29 23:03
 */
class MineFragment: BaseFragment() {

    private lateinit var mineDataBinding: FragmentMineBinding

    companion object

    val TAG = "MineFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mineDataBinding = FragmentMineBinding.inflate(inflater, container, false)
        context ?: return mineDataBinding.root

        return mineDataBinding.root
    }

    override fun setLayoutResId(): Int = R.layout.fragment_mine

    override fun initView() {
        mineDataBinding.mineCenterButton.setOnClickListener {
//            findNavController().navigate(R.id.nav_user_center_fragment)
//            startActivity(Intent(activity, UserCenterActivity::class.java))
            Log.d(TAG, "点击了按钮")
        }

    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}








