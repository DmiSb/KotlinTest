package com.dmi.kotlintest.data.network.core

import com.dmi.kotlintest.data.model.DataInfo
import com.dmi.kotlintest.data.model.PhoneMask
import com.dmi.kotlintest.data.network.responce.AuthRes
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RestService {

    @GET("phoneMask.php")
    fun getPhoneMask() : Single<Response<PhoneMask>>

    @POST("auth.php")
    @FormUrlEncoded
    fun auth(
            @Field("phone") phone: String,
            @Field("password") password: String
    ) : Single<Response<AuthRes>>

    @GET(".")
    fun getEndpoints() : Single<Response<List<DataInfo>>>
}