package com.zzj.media.ui

import android.os.Bundle
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaDetailsPresenter
import com.zzj.media.presenter.view.MediaDetailsView

class MediaDetailsFragment : BaseMvpFragment<MediaDetailsPresenter>(),MediaDetailsView{

    var url = "";

    fun newInstance(url:String ): MediaDetailsFragment {
        val args = Bundle()
        val fragment = MediaDetailsFragment()
        args.putString("url",url)
        fragment.setArguments(args)
        return fragment
    }

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
        url = arguments?.getString("url").toString()
        mPresenter.getDetailsData(url)
    }

    override fun initView() {
    }

}