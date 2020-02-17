package com.zzj.baselibrary.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.gyf.immersionbar.components.SimpleImmersionProxy
import com.zzj.baselibrary.R
import com.zzj.baselibrary.dialog.LoadingDialog
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() , SimpleImmersionOwner {

    lateinit var rootView: View

    /**
     * ImmersionBar代理类
     */
    private val mSimpleImmersionProxy = SimpleImmersionProxy(this)

    var loadingDialog: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = getContainerLayout(savedInstanceState)
        rootView = inflater.inflate(layoutId, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar)
        }
    }


    protected abstract fun initListener()


    abstract fun getContainerLayout(savedInstanceState: Bundle?): Int


    protected abstract fun initData()


    protected abstract fun initView()


    override fun initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(false).init()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mSimpleImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mSimpleImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean {
        return true
    }

    fun loading(msg:String){
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.Builder(_mActivity)
                .setCancelable(false)
                .setCancelOutside(false)
                .setMessage(msg)
                .setShowMessage(true)
                .create()
        }

        loadingDialog?.show()
    }

    fun dismiss(){
        if (loadingDialog != null) {
            loadingDialog?.dismiss()
        }
    }
}