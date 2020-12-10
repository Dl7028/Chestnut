package com.yks.chestnutyun.customView

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.yks.chestnutyun.R
import kotlinx.android.synthetic.main.fragment_progress_dialog.*

/**
  * @Description:    网络加载情况对话框
  * @Author:         Yu ki-r
  * @CreateDate:     2020/11/9 17:38
 */

class ProgressDialogFragment : DialogFragment() {

    private var messageResId: Int? = null

    companion object {
        fun newInstance() = ProgressDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMessage.text = getString(messageResId ?: R.string.loading)
    }

    fun show(
        fragmentManager: FragmentManager,
        @StringRes messageResId: Int,
        isCancelable: Boolean = false
    ) {
        this.messageResId = messageResId
        this.isCancelable = isCancelable
        try {
//            fragmentManager.beginTransaction().remove(this).commit()  //每次show 之前移除之前的fragment
            show(fragmentManager, "progressDialogFragment")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}