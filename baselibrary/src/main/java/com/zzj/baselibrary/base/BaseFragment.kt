package com.zzj.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){

    lateinit var rootView:View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(getContainerLayout(savedInstanceState) != 0){
            rootView = inflater.inflate(getContainerLayout(savedInstanceState),container,false)
        }

        initView()
        initData()
        initListener()
        return rootView
    }

    protected abstract fun initListener()


    abstract fun getContainerLayout(savedInstanceState: Bundle?): Int


    protected abstract fun initData()


    protected abstract fun initView()

}