package com.zzj.baselibrary.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * base
 */
abstract class BaseActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getContainerLayout() != 0){
            setContentView(getContainerLayout())
        }

    }

    protected abstract fun initListener()


    abstract fun getContainerLayout(): Int


    protected abstract fun initData()


    protected abstract fun initView()

}