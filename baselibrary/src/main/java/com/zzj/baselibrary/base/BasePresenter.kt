package com.zzj.baselibrary.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDisposeConverter
import com.zzj.baselibrary.rx.bindLifecycle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

@Suppress("UNCHECKED_CAST")
open class BasePresenter<V :BaseView> :IPresenter,AnkoLogger{


    lateinit var lifecycleOwner: LifecycleOwner




    var mView: V? = null



    protected var TAG = ""

    /**
     * 绑定View
     * @param view
     */
    fun attachView(view: V) {
        TAG = this.javaClass.simpleName
        mView = view

    }

    protected fun <T> bindLifecycle(): AutoDisposeConverter<T> {
        return bindLifecycle(lifecycleOwner)
    }
    /**
     * 解绑View
     */
    fun detachView() {


    }

    fun getView(): V? {
        return mView
    }


    override fun onCreate(owner: LifecycleOwner) {
        error { "${TAG}+---->onCreate" }
        lifecycleOwner = owner
    }

    override fun onStart(owner: LifecycleOwner) {
    }

    override fun onResume(owner: LifecycleOwner) {
    }

    override fun onPause(owner: LifecycleOwner) {
    }

    override fun onStop(owner: LifecycleOwner) {
    }

    override fun onDestroy(owner: LifecycleOwner) {
        error { "${TAG}+---->onDestroy" }
        detachView()
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
    }
}