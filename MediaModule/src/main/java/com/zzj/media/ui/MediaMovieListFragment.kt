package com.zzj.media.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.zzj.baselibrary.base.BaseFragment
import com.zzj.baselibrary.http.RetrofitClient
import com.zzj.baselibrary.rx.dataConvert
import com.zzj.baselibrary.rx.exceptionTransformer
import com.zzj.baselibrary.rx.transform
import com.zzj.media.R
import com.zzj.media.api.APIService
import com.zzj.media.data.MovieBean
import kotlinx.android.synthetic.main.media_fragment_movice_list.*
import me.yokeyword.fragmentation.ISupportFragment

/**
 * 首页视频列表
 */
class MediaMovieListFragment :BaseFragment(){


    var type:String = ""

    var page:Int = 1

    var movieListAdapter:MovieListAdapter? = null

    var isFirst = false

    fun newInstance(type:String): MediaMovieListFragment {

        val args = Bundle()
        args.putString("type",type)
        val fragment = MediaMovieListFragment()
        fragment.setArguments(args)
        return fragment
    }

    override fun getContainerLayout(savedInstanceState: Bundle?): Int {

        return R.layout.media_fragment_movice_list
    }


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (!isFirst){
            isFirst = true
            refreshLayout.autoRefresh()
        }
    }

    override fun initData() {


    }

    override fun initView() {
        type = arguments?.getString("type").toString()
        recyclerView.layoutManager = GridLayoutManager(_mActivity,2)
        movieListAdapter = MovieListAdapter()
        recyclerView.adapter = movieListAdapter
    }

    inner class MovieListAdapter : BaseQuickAdapter<MovieBean,BaseViewHolder>(R.layout.item_section_content){
        override fun convert(helper: BaseViewHolder, item: MovieBean?) {
            Glide.with(mContext).load(item?.picture).apply(RequestOptions().placeholder(R.color.grey_200))
                .into(helper.getView(R.id.iv) as ImageView)
            helper.setText(R.id.tv, item?.title)
        }

    }

    override fun initListener() {
        refreshLayout.setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page ++
                getListData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                getListData()
            }

        })

        movieListAdapter?.setOnItemClickListener { adapter, view, position ->
            _mActivity.start(MediaDetailsFragment().newInstance((adapter.data[position] as MovieBean).id), ISupportFragment.SINGLETOP)

        }
    }


    fun getListData(){
        RetrofitClient.instance.create(APIService::class.java)
            .getMovieList(page,type)
            .dataConvert()
            .compose(exceptionTransformer())
            .transform(this)
            .subscribe({
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadMore()
                if(page == 1){
                    movieListAdapter?.data?.clear()
                }
                movieListAdapter?.addData(it)

            },{
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadMore()
                LogUtils.e("TAG",it.message)
            })
    }

}