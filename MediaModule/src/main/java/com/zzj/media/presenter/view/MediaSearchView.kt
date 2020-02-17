package com.zzj.media.presenter.view

import com.zzj.baselibrary.base.BaseView
import com.zzj.media.data.MovieDetailsBean

interface MediaSearchView :BaseView{

    /**
     * 搜索
     */
    fun searchSuccess(detailsBean:List<MovieDetailsBean>)



}