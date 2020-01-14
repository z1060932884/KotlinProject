package com.zzj.media.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.data.MovieBean
import com.zzj.media.presenter.MediaHomePresenter
import com.zzj.media.presenter.view.MediaHomeView
import kotlinx.android.synthetic.main.fragment_media_home.*
import me.yokeyword.fragmentation.ISupportFragment

class MediaHomeFragment : BaseMvpFragment<MediaHomePresenter>(), MediaHomeView,
    BGABanner.Adapter<View, MovieBean> {


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
        bgaBanner.setAdapter(this)
        bgaBanner.setData(movieBeans, null)
        bgaBanner.setDelegate { banner, itemView, model, position ->
            mActivity.start(MediaDetailsFragment(), ISupportFragment.SINGLETOP)
        }

    }


    override fun createPresenter(): MediaHomePresenter {

        return MediaHomePresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_media_home
    }

    override fun initData() {
        mPresenter.getData()
    }

    override fun initView() {

    }


}