package com.zzj.baselibrary.base

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import org.jetbrains.annotations.NotNull

abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment(),BaseView{


     var TAG = ""
     lateinit var mPresenter: P

     lateinit var mActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = this.javaClass.simpleName
        mActivity = (context as Activity)

        mPresenter = createPresenter()
        initLifecycleObserver(this.lifecycle)
        attachPresenterView()

    }



    private fun initLifecycleObserver(@NotNull lifecycle: Lifecycle) {
//        mPresenter.lifecycleOwner(lifecycle)
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