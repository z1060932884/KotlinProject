package com.zzj.media

import com.zzj.baselibrary.base.BaseMvpActivity
import com.zzj.media.presenter.MediaPresenter
import com.zzj.media.presenter.view.MediaView
import kotlinx.android.synthetic.main.act_media.*
import org.jetbrains.anko.toast

class MediaActivity : BaseMvpActivity<MediaPresenter>(),MediaView {



    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }


    override fun getDataSuccess(paramter: String) {
        toast("获取数据成功")
    }


    override fun initListener() {

    }

    override fun getContainerLayout(): Int {

        return R.layout.act_media
    }


    override fun initData() {
    }

    override fun initView() {

        tvMedia.setOnClickListener {
            toast("media")
            mPresenter.getData()
        }

    }

    override fun createPresenter(): MediaPresenter {
        return MediaPresenter()
    }



}