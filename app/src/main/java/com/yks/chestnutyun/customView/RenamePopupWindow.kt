package com.yks.chestnutyun.customView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.yks.chestnutyun.R
import com.yks.chestnutyun.utils.ToastUtil

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/10 22:45
 */
@SuppressLint("ClickableViewAccessibility")
class RenamePopupWindow(context: Activity, fileName: String?) : PopupWindow(context) {
    private val mcontext: Context
    private val RenameView: View
    private val cancelBtn: TextView
    private val confirmBtn: TextView
    private val newName: EditText

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?): RenamePopupWindow {
        this.onItemClickListener = onItemClickListener
        return this
    }

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onOkClick(nickName: String?)
    }

    init {
        mcontext = context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        RenameView = inflater.inflate(R.layout.rename_popup_layout, null)
        cancelBtn = RenameView.findViewById(R.id.rename_cannel)
        confirmBtn = RenameView.findViewById(R.id.rename_sure)
        newName = RenameView.findViewById(R.id.rename_input)
        newName.requestFocus()
        newName.setText(fileName)
        confirmBtn.setOnClickListener(View.OnClickListener {
            if ((newName.text.toString().trim { it <= ' ' }).isEmpty()) {
                ToastUtil.showToast("请输入新的文件名!")
                return@OnClickListener
            }
            onItemClickListener!!.onOkClick(newName.text.toString().trim {
                it <= ' '
            })
        })
        cancelBtn.setOnClickListener {
            dismiss()
        }
        // 设置SelectPicPopupWindow的View
        this.contentView = RenameView
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.AnimBottomPopup
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(0)
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw)
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        RenameView.setOnTouchListener { v, event ->

            val height = RenameView.findViewById<View>(R.id.rename_popup).top
            val y = event!!.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
    }

}