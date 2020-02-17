package com.zzj.media.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.media.R
import com.zzj.media.data.MovieDetailsBean
import kotlinx.android.synthetic.main.media_fragment_search_content.*

class MediaSearchContentFragment : BaseFragment(){

    var adapter:SearchAdapter? = null

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_search_content
    }

    override fun initData() {

    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(_mActivity)
        adapter = SearchAdapter()
        recyclerView.adapter = adapter
    }



    override fun initListener() {
        adapter?.setOnItemClickListener { adapter, view, position ->
            _mActivity.start(MediaDetailsFragment().newInstance(adapter.data[position] as MovieDetailsBean))
        }
    }

    /**
     * 加载搜索的数据
     */
    fun loadData(detailsBean: List<MovieDetailsBean>){
        adapter?.data?.clear()
        adapter?.addData(detailsBean)
    }
    /**
     * 加载搜索的数据
     */
    fun clearData(){
        adapter?.data?.clear()
        adapter?.notifyDataSetChanged()
    }


    inner class SearchAdapter : BaseQuickAdapter<MovieDetailsBean,BaseViewHolder>(R.layout.media_item_search_content){
        override fun convert(helper: BaseViewHolder, item: MovieDetailsBean?) {
            Glide.with(mContext).load(item?.picture).apply(RequestOptions().placeholder(R.color.grey_200))
                .into(helper.getView(R.id.picture) as ImageView)
            helper.setText(R.id.tv_title, item?.title)
            helper.setText(R.id.tvTime, "${item?.showTime} · ${item?.type} · ${item?.remark}")
            helper.setText(R.id.tvActors, "主演 : ${item?.actor}")
            helper.setText(R.id.tvStatus, "${item?.status}")
        }

    }



}