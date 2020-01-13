package com.zzj.media.presenter.view

import com.zzj.baselibrary.base.BaseView
import com.zzj.media.data.MovieBean

interface MediaHomeView :BaseView{

    fun getDataSuccess(movieBeans: List<MovieBean>)

}