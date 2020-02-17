package com.zzj.baselibrary.base

import android.os.Bundle
import com.zzj.baselibrary.dialog.LoadingDialog
import me.yokeyword.fragmentation.SupportActivity


/**
 * base
 */
abstract class BaseActivity : SupportActivity(){


    var loadingDialog: LoadingDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getContainerLayout() != 0){
            setContentView(getContainerLayout())
        }
        initView()
        initData()
        initListener()

    }

    abstract fun getContainerLayout(): Int

    protected abstract fun initListener()


    protected abstract fun initData()


    protected abstract fun initView()

    fun loading(msg:String){
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.Builder(this)
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