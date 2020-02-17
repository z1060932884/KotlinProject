package com.zzj.baselibrary.rx

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider.from
import com.uber.autodispose.autoDispose
import com.zzj.baselibrary.http.ExceptionHandle
import com.zzj.baselibrary.http.HttpResult
import com.zzj.baselibrary.http.ServerException
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


fun <T> io_main(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}


/*
* 对Observable进行线程调度和生命周期绑定
*
* */
fun <T> Observable<T>.transform(owner: LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.compose(io_main())
        .autoDispose(from(owner, Lifecycle.Event.ON_DESTROY))
}


fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
    return AutoDispose.autoDisposable(
        AndroidLifecycleScopeProvider.from(lifecycleOwner)
    )
}



fun <T> exceptionTransformer(): ObservableTransformer<T,T> {

    return ObservableTransformer { upstream ->
        upstream.onErrorResumeNext(HttpResultFunction<T>())
    }

}

/*
* 数据转换
*
* */
fun <T> Observable<HttpResult<T>>.dataConvert(): Observable<T> {
    return flatMap {
        if (it.isSuccess) Observable.just(it.result) else Observable.error(Throwable(message = it.message))
    }
}


/*private class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(ExceptionHandle.handleException(t))
    }
}*/

/*class HandleFuc<T> : Function<HttpResult<T>, T> {
    override fun apply(response: HttpResult<T>): T {

        if (response.code !== 200)
            throw RuntimeException(response.message)

        return response?
    }
}*/

class ResultFunction<T> : Function<HttpResult<T>, T> {
    override fun apply(httpResult: HttpResult<T>): T {
        if (!httpResult.isSuccess) {
            throw ServerException(httpResult.code, httpResult.message!!)
        }
        return httpResult.result!!
    }
}

class HttpResultFunction<T> : Function<Throwable, Observable<T>> {
    @Throws(Exception::class)
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        //打印具体错误
        return Observable.error(ExceptionHandle.handleException(throwable))
    }
}
