package com.zzj.user

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
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
            info { "ahsdhashdahsd哈哈时间啊还是简单哈数据的撒回到家" }
        }
    }

}