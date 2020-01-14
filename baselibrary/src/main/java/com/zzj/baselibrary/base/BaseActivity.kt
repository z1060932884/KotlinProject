package com.zzj.baselibrary.base

import android.os.Bundle
import me.yokeyword.fragmentation.SupportActivity


/**
 * base
 */
abstract class BaseActivity : SupportActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getContainerLayout() != 0){
            setContentView(getContainerLayout())
        }


    }

    abstract fun getContainerLayout(): Int




}