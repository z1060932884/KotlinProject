package com.zzj.media.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzj.media.R
import com.zzj.media.data.MovieUrlBean

class MediaDetailsPlayUrlAdapter(layoutResId: Int) :

    BaseQuickAdapter<MovieUrlBean, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: MovieUrlBean?) {

        helper.setText(R.id.tvTitle,item?.title)

        if(item!!.isSelect) {
            helper.setTextColor(R.id.tvTitle,mContext.resources.getColor(R.color.orange_400))
        }else{
            helper.setTextColor(R.id.tvTitle,mContext.resources.getColor(R.color.base_text_dark))

        }
    }

}