package com.zzj.media.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.media.R
import kotlinx.android.synthetic.main.media_fragment_main.view.*

class MediaMainFragment : BaseFragment() {

    val FIRST = 0
    val SECOND = 1
    val THIRD = 2
    val FOURTH = 3
    private val mFragments = arrayOfNulls<BaseFragment>(2)

    override fun initListener() {
        /**
         * 底部栏的点击事件
         */
        rootView.bottomBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.media_home) {
                showHideFragment(mFragments[FIRST])
                return@OnNavigationItemSelectedListener true
            } else if (item.itemId == R.id.media_mine) {
                showHideFragment(mFragments[SECOND])
                return@OnNavigationItemSelectedListener true
            }
            false
        })

    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_main
    }

    override fun initData() {
        val firstFragment = findFragment(MediaHomeFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = MediaFragment()
            mFragments[SECOND] = MediaDetailsFragment()
            loadMultipleRootFragment(
                R.id.fl_container, FIRST,
                mFragments[FIRST], mFragments[SECOND]
            )

        } else {
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(MediaDetailsFragment::class.java)
        }
    }

    override fun initView() {

    }

}