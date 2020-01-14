package com.zzj.media.ui

import android.os.Bundle
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView

class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(),MediaDetailsView{

    override fun createPresenter(): MediaDetailsPresenter {
        return MediaDetailsPresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragemnt_details
    }

    override fun initData() {
    }

    override fun initView() {
    }

}