package com.zzj.baselibrary.base

import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

open class BasePresenter<V :BaseView> {

    private var mViewReference: WeakReference<V>? = null

    lateinit var mProxyView: V
    protected var lifecycleOwner: LifecycleOwner? = null

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
        ) { proxy, method, args ->
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
        //       if(mProxyView!=null){
        //            mProxyView = null;
        //       }

    }

    fun getView(): V {
        return mProxyView
    }
}