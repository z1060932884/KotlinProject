package com.zzj.baselibrary

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV
import com.tencent.smtt.sdk.QbSdk
import me.yokeyword.fragmentation.Fragmentation

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化 缓存类
        MMKV.initialize(this)
        Fragmentation.builder()
            // show stack view. Mode: BUBBLE, SHAKE, NONE
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG)
        .install()

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e("QbSdk", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb)
    }




}
