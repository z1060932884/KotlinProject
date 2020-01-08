package com.zzj.baselibrary.base

import android.os.Bundle

open abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity(),BaseView{

    lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onLoading(message: String) {

    }

    override fun onError(code: Int, message: String) {

    }

    override fun onDismiss() {

    }

}