package com.zzj.media.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzj.media.R
import com.zzj.media.data.MovieBean

class MediaDetailsPlayUrlAdapter(layoutResId: Int) :

    BaseQuickAdapter<MovieBean, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: MovieBean?) {

        helper.setText(R.id.tvTitle,item?.title)
    }

}