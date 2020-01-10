package com.zzj.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

/**
 * 注册
 */
class RegisterActivity : AppCompatActivity(),AnkoLogger {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_register)
        mTvRegister.setOnClickListener {
            toast("注册")
            error { "ahsdhashdahsd哈哈时间啊还是简单哈数据的撒回到家" }
        }
    }
/*
    override fun createPresenter(): BasePresenter<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun initData() {
    }

    override fun initView() {
        mTvRegister.setOnClickListener {
            toast("注册")
            error { "ahsdhashdahsd哈哈时间啊还是简单哈数据的撒回到家" }
        }
    }

    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return R.layout.act_register
    }*/


}