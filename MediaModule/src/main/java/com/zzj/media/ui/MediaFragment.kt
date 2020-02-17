package com.zzj.media.ui

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.media.R
import com.zzj.media.presenter.MediaPresenter
import com.zzj.media.presenter.view.MediaView
import kotlinx.android.synthetic.main.media_fragment_media.*
import kotlinx.android.synthetic.main.media_fragment_media.view.*
import java.util.*


class MediaFragment :BaseMvpFragment<MediaPresenter>(), MediaView {
    override fun getDataSuccess(paramter: String) {
    }

    override fun createPresenter(): MediaPresenter {
        return MediaPresenter()
    }
    //https://1090ys.com/
    private val titles = arrayOf("首页", "国产剧","香港剧","台湾剧","日本剧","韩国剧","欧美剧"
        ,"海外剧","综艺节目","动漫剧场")
    private val fragments = ArrayList<BaseFragment>()

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }
    override fun initListener() {
        /**
         * 搜索点击事件
         */
        rlSearch.setOnClickListener {
            mActivity.start(MediaSearchFragment())
        }
        /**
         * tab选中事件
         */
        tabSegment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = getTitleTextView()
                textView.setText(tab.text)
                tab.setCustomView(textView)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.setCustomView(null)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }


    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_media
    }

    override fun initData() {
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this,rootView?.tabSegment)
        fragments.add(MediaHomeFragment())
        fragments.add(MediaMovieListFragment().newInstance(titles[1]))
        fragments.add(MediaMovieListFragment().newInstance(titles[2]))
        fragments.add(MediaMovieListFragment().newInstance(titles[3]))
        fragments.add(MediaMovieListFragment().newInstance(titles[4]))
        fragments.add(MediaMovieListFragment().newInstance(titles[5]))
        fragments.add(MediaMovieListFragment().newInstance(titles[6]))
        fragments.add(MediaMovieListFragment().newInstance(titles[7]))
        fragments.add(MediaMovieListFragment().newInstance(titles[8]))
        fragments.add(MediaMovieListFragment().newInstance(titles[9]))
        var mViewPagerAdapter = ViewPagerAdapter(fragmentManager!!)
        rootView!!.contentViewPager.setAdapter(mViewPagerAdapter)
        rootView?.tabSegment.setupWithViewPager(rootView?.contentViewPager)

        val textView = getTitleTextView()
        textView.setText(titles[0])
        tabSegment.getTabAt(0)?.setCustomView(textView)

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

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//            super.destroyItem(container, position, `object`)
        }
    }

    fun getTitleTextView():TextView{
        val selectTitleTextView = TextView(mActivity)
        val selectedSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            23F,
            resources.displayMetrics
        )
        selectTitleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize)
        selectTitleTextView?.setTextColor(resources.getColor(R.color.base_blue))
        selectTitleTextView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

        return selectTitleTextView

    }

}
