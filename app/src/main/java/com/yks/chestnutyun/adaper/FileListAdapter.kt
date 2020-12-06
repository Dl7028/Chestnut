package com.yks.chestnutyun.adaper

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yks.chestnutyun.R
import com.yks.chestnutyun.data.bean.FileItem
import com.yks.chestnutyun.utils.ActivityHelper

/**
 * @Description:
 * @Author:         Yu ki-r
 * @CreateDate:     2020/12/6 23:05
 */
class FileListAdapter(layoutResId: Int) : BaseQuickAdapter<FileItem, BaseViewHolder>(layoutResId) {



    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * 实现此方法，并使用 helper 完成 item 视图的操作
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(holder: BaseViewHolder, item: FileItem) {
        holder.apply {
            setText(R.id.item_file_name, item.filename)
            setText(R.id.item_file_size, item.size)
            setText(R.id.item_file_date, item.updateTime)
        }
        val imageView: ImageView = holder.getView(R.id.item_file_image)

        if (item.imageURL.isNotEmpty()){
            Glide.with(ActivityHelper.getCurrentActivity()).load(item.imageURL).into(imageView)
        }

    }
}