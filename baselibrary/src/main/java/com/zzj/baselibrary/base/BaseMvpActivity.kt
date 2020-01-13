package com.zzj.baselibrary.base

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import org.jetbrains.annotations.NotNull

abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity(),BaseView{

    lateinit var mPresenter: P


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
        initLifecycleObserver(this.lifecycle)
        attachPresenterView()
        initView()
        initData()
        initListener()

    }


    private fun initLifecycleObserver(@NotNull lifecycle: Lifecycle) {
        mPresenter.lifecycleOwner = this
        lifecycle.addObserver(mPresenter)
//        mPresenter?.let { lifecycle.addObserver(it) }
    }




    abstract fun createPresenter():P

    abstract fun attachPresenterView()

    override fun onLoading(message: String) {

    }

    override fun onError(code: Int, message: String) {

    }

    override fun onDismiss() {

    }

}

