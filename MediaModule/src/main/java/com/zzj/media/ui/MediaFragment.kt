package com.zzj.media.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaPresenter
import com.zzj.media.presenter.view.MediaView
import kotlinx.android.synthetic.main.media_fragment_media.view.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.ArrayList

class MediaFragment :BaseMvpFragment<MediaPresenter>(), MediaView {
    override fun getDataSuccess(paramter: String) {
    }

    override fun createPresenter(): MediaPresenter {
        return MediaPresenter()
    }
    //https://1090ys.com/

    private val titles = arrayOf("首页", "电影","美剧","韩剧","日剧","国产剧","动漫")
    private val fragments = ArrayList<BaseMvpFragment<*>>()

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }
    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_media
    }

    override fun initData() {
    }

    override fun initView() {
        //        setHasOptionsMenu(true)
//        ToolbarHelper(this, toolbar, "", false)
        fragments.add(MediaHomeFragment())
        fragments.add(MediaHomeFragment())
        fragments.add(MediaHomeFragment())
        fragments.add(MediaHomeFragment())
        fragments.add(MediaHomeFragment())
        fragments.add(MediaHomeFragment())
        fragments.add(MediaDetailsFragment())
        var mViewPagerAdapter = ViewPagerAdapter(fragmentManager!!)
        rootView!!.contentViewPager.setAdapter(mViewPagerAdapter)
        val commonNavigator = CommonNavigator(mActivity)
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles?.size ?: 0
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = titles[index]
                simplePagerTitleView.normalColor = resources.getColor(R.color.base_text_light)
                simplePagerTitleView.selectedColor = resources.getColor(R.color.base_text_dark)
                simplePagerTitleView.textSize = 16f
                simplePagerTitleView.setOnClickListener { rootView!!.contentViewPager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.yOffset = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.setColors(resources.getColor(R.color.base_text_dark))
                return indicator
            }
        }
        rootView!!.tabSegment.setNavigator(commonNavigator)
        ViewPagerHelper.bind(rootView!!.tabSegment, rootView!!.contentViewPager)
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var fm: FragmentManager? = null

        init {
            this.fm = fm
        }

        override fun getItem(position: Int): Fragment {
            return fragments.get(position)
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}
