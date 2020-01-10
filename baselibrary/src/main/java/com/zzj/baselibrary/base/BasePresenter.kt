package com.zzj.baselibrary.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

@Suppress("UNCHECKED_CAST")
open class BasePresenter<V :BaseView> :IPresenter,AnkoLogger{


    var lifecycleOwner: LifecycleOwner? = null



    private var mViewReference: WeakReference<V>? = null

    lateinit var mProxyView: V



    protected var TAG = ""

    /**
     * 绑定View
     * @param view
     */
    fun attachView(view: V) {
        TAG = this.javaClass.simpleName
        this.mViewReference = WeakReference(view)
        //使用代理对象
        mProxyView = Proxy.newProxyInstance(
            view.javaClass.classLoader, view.javaClass.interfaces
        ) { _, method, args ->
            //执行这个方法，调用的是委托对象，判断View是否为空
            if (mViewReference == null || mViewReference!!.get() == null) {
                null
            } else method.invoke(mViewReference!!.get(), *args)
            //没解绑执行的是原始被代理 View的方法
        } as V

    }


    /**
     * 解绑View
     */
    fun detachView() {
        if (mViewReference != null) {
            mViewReference!!.clear()
            mViewReference = null
        }

    }

    fun getView(): V {
        return mProxyView
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