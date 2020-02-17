package com.zzj.media.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzj.media.R
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MySection

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class SectionAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param sectionHeadResId The section head layout id for each item
 * @param layoutResId      The layout resource id of each item.
 * @param data             A new list is created out of this one to avoid mutable list
 */
    (layoutResId: Int, sectionHeadResId: Int, data: List<*>) :
    BaseSectionQuickAdapter<MySection, BaseViewHolder>(layoutResId, sectionHeadResId,
        data as MutableList<MySection>?
    ) {

    override fun convertHead(helper: BaseViewHolder, item: MySection) {
        helper.setText(R.id.header, item.header)
        helper.setVisible(R.id.more, item.isMore)
        helper.addOnClickListener(R.id.more)
    }


    override fun convert(helper: BaseViewHolder, item: MySection) {
        val video = item.t as MovieBean
        Glide.with(mContext).load(video.picture).apply(RequestOptions().placeholder(R.color.grey_200))
            .into(helper.getView(R.id.iv) as ImageView)
        helper.setText(R.id.tv, video.title)
    }
}
