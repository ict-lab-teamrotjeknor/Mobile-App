package com.hro.ictlab.ict_lab.retrofit

import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface ApiModule {

    @POST("register.json")
    fun register(@Query("username") username: String): Observable<String>

}