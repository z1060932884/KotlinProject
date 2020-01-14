package com.zzj.baselibrary

import android.app.Application
import me.yokeyword.fragmentation.Fragmentation

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fragmentation.builder()
            // show stack view. Mode: BUBBLE, SHAKE, NONE
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG)
        .install()
    }




}
