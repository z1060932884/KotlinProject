package com.zzj.media

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.data.MovieBean
import com.zzj.media.presenter.MediaHomePresenter
import com.zzj.media.presenter.view.MediaHomeView

class MediaHomeFragment :BaseMvpFragment<MediaHomePresenter>(),MediaHomeView{
    override fun getDataSuccess(movieBeans: List<MovieBean>) {
        LogUtils.e("getDataSuccess",movieBeans.get(0).title)
    }



    override fun createPresenter(): MediaHomePresenter {

        return MediaHomePresenter()
    }

    override fun attachPresenterView() {

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