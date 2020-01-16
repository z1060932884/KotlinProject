package com.zzj.baselibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {

    lateinit var rootView: View


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
    }

    protected abstract fun initListener()


    abstract fun getContainerLayout(savedInstanceState: Bundle?): Int


    protected abstract fun initData()


    protected abstract fun initView()

}