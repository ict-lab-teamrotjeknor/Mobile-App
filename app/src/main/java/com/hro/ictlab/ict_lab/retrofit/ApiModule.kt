package com.hro.ictlab.ict_lab.retrofit

import okhttp3.ResponseBody
import retrofit2.http.*
import rx.Observable

interface ApiModule {

    @Headers("Content-Type: application/json")

    @POST("authentication/signup")
    fun register(@Body form: AuthenticationForm): Observable<BaseResponse>

    @POST("authentication/signin")
    fun signIn(@Body form: AuthenticationForm): Observable<AuthenticationResponse>

    @GET("schedule/getuserreservations")
    fun getUserReservations(): Observable<BaseResponse>
}