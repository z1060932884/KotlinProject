package com.zzj.media.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zzj.baselibrary.base.BaseMvpActivity
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.baselibrary.base.BasePresenter
import com.zzj.baselibrary.base.BaseView
import com.zzj.media.R
import com.zzj.media.presenter.MediaPresenter
import com.zzj.media.presenter.view.MediaView
import kotlinx.android.synthetic.main.act_media.*
import kotlinx.android.synthetic.main.media_fragment_media.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import org.jetbrains.anko.toast
import java.util.*

class MediaActivity : BaseMvpActivity<BasePresenter<*>>(),BaseView {


    override fun attachPresenterView() {
    }





    override fun initListener() {

    }

    override fun getContainerLayout(): Int {

        return R.layout.act_media
    }


    override fun initData() {
    }

    override fun initView() {
        loadRootFragment(R.id.fl_container,MediaFragment())
    }

    override fun createPresenter(): BasePresenter<BaseView> {
        return BasePresenter()
    }



}