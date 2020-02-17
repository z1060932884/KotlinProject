package com.zzj.media.ui

import com.zzj.baselibrary.base.BaseMvpActivity
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.base.BaseView
import com.zzj.media.R

class MediaActivity : BaseMvpActivity<BasePresenter<*>>(),BaseView {


    override fun attachPresenterView() {
    }


    override fun initListener() {

    }

    override fun getContainerLayout(): Int {

        return R.layout.act_media
    }


    override fun initData() {
//        CrashReport.testJavaCrash();
    }

    override fun initView() {
        loadRootFragment(R.id.fl_container,MediaMainFragment())
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter()
    }



}