package com.yks.chestnutyun.views.member

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
        import android.view.View
        import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yks.chestnutyun.MainActivity
import com.yks.chestnutyun.R
import com.yks.chestnutyun.views.base.BaseFragment
import com.yks.chestnutyun.databinding.FragmentMineBinding
import kotlinx.android.synthetic.main.fragment_login.*

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
            val bundle = requireActivity().intent.extras!!
            val intent = Intent()
            intent.putExtra("username", bundle.getString("username")!!)
            intent.setClass(requireActivity(), UserActivity::class.java)
            requireActivity().startActivity(intent)
        }

    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}








