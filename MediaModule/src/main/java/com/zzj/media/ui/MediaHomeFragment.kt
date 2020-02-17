package com.zzj.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.adapter.SectionAdapter
import com.zzj.media.data.MovieBean
import com.zzj.media.data.MySection
import com.zzj.media.presenter.MediaHomePresenter
import com.zzj.media.presenter.view.MediaHomeView
import kotlinx.android.synthetic.main.fragment_media_home.*
import me.yokeyword.fragmentation.ISupportFragment

class MediaHomeFragment : BaseMvpFragment<MediaHomePresenter>(), MediaHomeView,
    BGABanner.Adapter<View, MovieBean> {
    var bgaBanner:BGABanner? = null
    var mData = arrayListOf<MySection>()
    var sectionAdapter: SectionAdapter? = null
    //判断是否已经加载
    var isFirstInit:Boolean = false

    override fun getListData(list: List<MySection>) {
        sectionAdapter?.setNewData(list)
    }


    override fun fillBannerItem(
        banner: BGABanner?,
        itemView: View?,
        model: MovieBean?,
        position: Int
    ) {
        Glide.with(mActivity).load(model!!.picture)
            .into(itemView as ImageView)
    }

    override fun getBannerDataSuccess(movieBeans: List<MovieBean>) {
        bgaBanner?.setAdapter(this)
        bgaBanner?.setData(movieBeans, null)
        bgaBanner?.setDelegate { banner, itemView, model, position ->
//            mActivity.start(MediaDetailsFragment().newInstance((model as MovieBean).url), ISupportFragment.SINGLETOP)
        }

    }


    override fun createPresenter(): MediaHomePresenter {

        return MediaHomePresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {

        sectionAdapter?.setOnItemClickListener { adapter, view, position ->
            mActivity.start(MediaDetailsFragment().newInstance((adapter.data[position] as MySection).t.id), ISupportFragment.SINGLETOP)

        }

        refreshLayout.setOnRefreshListener {
            mPresenter.getData()
        }
    }

    override fun onDismiss() {
        super.onDismiss()
        refreshLayout.finishRefresh()
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_media_home
    }

    override fun initData() {

    }



    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if(!isFirstInit){
            isFirstInit = true
            onLoading("")
            mPresenter.getData()
        }

    }

    override fun initView() {
        recyclerView.layoutManager = GridLayoutManager(mActivity, 2)

         sectionAdapter =
            SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData)
        recyclerView.adapter = sectionAdapter

        val view:View = LayoutInflater.from(mActivity).inflate(R.layout.include_banner,null);
        bgaBanner = view.findViewById(R.id.bgaBanner)
        sectionAdapter?.addHeaderView(view)
    }

}