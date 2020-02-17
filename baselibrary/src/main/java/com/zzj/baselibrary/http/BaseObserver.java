package com.zzj.baselibrary.http;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    public BaseObserver() {

    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e("BaseObserver", e.getMessage());
        // todo error somthing
        hideDialog();
        if(e instanceof ResponseThrowable){
            onError((ResponseThrowable)e);
        } else {
            onError(new ResponseThrowable(e, ExceptionHandle.ERROR.INSTANCE.getUNKNOWN()));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.e("建立连接");
        //可以弹出Dialog 提示正在加载
        showDialog();

    }

    protected abstract void hideDialog();

    protected abstract void showDialog();

    @Override
    public void onComplete() {
        LogUtils.e("请求完毕");
        //可以取消Dialog 加载完毕
        hideDialog();
    }


    public abstract void onError(ResponseThrowable e);
}
