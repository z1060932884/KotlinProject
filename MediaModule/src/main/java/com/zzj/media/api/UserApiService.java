package com.zzj.media.api;


import com.zzj.baselibrary.http.HttpResult;
import com.zzj.media.data.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.zzj.media.utils.MediaContanstKt.BASE_URL;


public interface UserApiService {

    @POST(BASE_URL+"smile/account/login")
    Observable<HttpResult<UserBean>> login(@Body UserBean userBean);
    @FormUrlEncoded
    @POST(BASE_URL+"smile/account/register")
    Observable<HttpResult<UserBean>> register(@Field("phone") String phone, @Field("password") String password);

    @GET(BASE_URL+"smile/account/getUserInfo")
    Observable<HttpResult<UserBean>> getUserInfo();

    @POST(BASE_URL+"smile/user/updateUserInfo")
    Observable<HttpResult<UserBean>> updateUserInfo(@Body UserBean userBean);

}
