package com.zzj.media.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.StringUtils
import com.gyf.immersionbar.ImmersionBar
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.baselibrary.base.BaseMvpFragment
import com.zzj.baselibrary.utils.MMKVUtils
import com.zzj.media.R
import com.zzj.media.data.MovieDetailsBean
import com.zzj.media.presenter.MediaSearchPresenter
import com.zzj.media.presenter.view.MediaSearchView
import com.zzj.media.utils.KEY_MEDIA_HISTORY
import kotlinx.android.synthetic.main.media_fragment_search.*

class MediaSearchFragment : BaseMvpFragment<MediaSearchPresenter>(),MediaSearchView{

    val FIRST = 0
    val SECOND = 1

    private val mFragments = arrayOfNulls<BaseFragment>(2)


    /**
     * 搜索数据成功，切换搜索界面
     */
    override fun searchSuccess(detailsBean: List<MovieDetailsBean>) {
        (mFragments[SECOND] as MediaSearchContentFragment).loadData(detailsBean)

    }

    override fun createPresenter(): MediaSearchPresenter {
        return MediaSearchPresenter()
    }

    override fun attachPresenterView() {
        mPresenter.attachView(this)
    }

    override fun initListener() {
        /**
         * 点击搜索事件
         */
        tvSearch.setOnClickListener {
            //添加关键字到历史集合中
            val content = etContent.text.toString()


            content?.let { it-> search(it) }

        }
        /**
         * 输入框监听
         */
        etContent.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //如果输入框为空  显示历史搜索和热门数据
                if(StringUtils.isEmpty(s)){
                    showHideFragment(mFragments[FIRST],mFragments[SECOND])
                }
            }

        })

    }

    /**
     * 执行搜索请求
     */
    fun search(content:String){
        etContent.setText(content)
        etContent.setSelection(content.length)
        var historyList =  MMKVUtils.getInstance().getStringSet(KEY_MEDIA_HISTORY)
        historyList.add(content)
        MMKVUtils.getInstance().put(KEY_MEDIA_HISTORY,historyList)
        //清空上次搜索的内容
        (mFragments[SECOND] as MediaSearchContentFragment).clearData()
        showHideFragment(mFragments[SECOND],mFragments[FIRST])
        mPresenter.search(content)
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {

        return R.layout.media_fragment_search
    }

    override fun initData() {
        val firstFragment = findFragment(MediaSearchContentFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = MediaHotAndHistorySearchFragment()
            mFragments[SECOND] = MediaSearchContentFragment()
            loadMultipleRootFragment(
                R.id.fl_container, FIRST,
                mFragments[FIRST], mFragments[SECOND]
            )
        } else {
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findFragment(MediaSearchContentFragment::class.java)
        }
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this,llTitle)
    }

}