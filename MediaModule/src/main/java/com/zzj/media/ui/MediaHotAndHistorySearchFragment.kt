package com.zzj.media.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.baselibrary.utils.MMKVUtils
import com.zzj.media.R
import com.zzj.media.presenter.MediaHotAndHistorySearchPresenter
import com.zzj.media.presenter.view.MediaHotAndHistorySearchView
import com.zzj.media.utils.KEY_MEDIA_HISTORY
import kotlinx.android.synthetic.main.media_fragment_hot_and_history_search.*

class MediaHotAndHistorySearchFragment :BaseMvpFragment<MediaHotAndHistorySearchPresenter>()
    ,MediaHotAndHistorySearchView{

    var searchFragment:MediaSearchFragment? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchFragment = parentFragment as MediaSearchFragment?
    }

    override fun createPresenter(): MediaHotAndHistorySearchPresenter {
        return MediaHotAndHistorySearchPresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {
        return R.layout.media_fragment_hot_and_history_search
    }

    override fun initData() {


    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        floatLayout.removeAllViews()
        var historyList =  MMKVUtils.getInstance().getStringSet(KEY_MEDIA_HISTORY)
        if(historyList.size == 0) rlHistorySearch.visibility = View.GONE else rlHistorySearch.visibility = View.VISIBLE
        for (element  in historyList){
            val view = LayoutInflater.from(mActivity).inflate(R.layout.media_layout_history_tv,null)
            val textView:TextView = view.findViewById(R.id.tvTitle);
            textView.setText(element)
            textView.setOnClickListener {
                searchFragment?.search(textView.text.toString())
            }
            floatLayout.addView(view)
        }
    }
    override fun initView() {
    }

}