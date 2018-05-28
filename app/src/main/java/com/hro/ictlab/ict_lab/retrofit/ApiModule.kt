package com.hro.ictlab.ict_lab.retrofit

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

interface ApiModule {

    @Headers("Content-Type: application/json")

    @POST("authentication/signup")
    fun register(@Body form: AuthenticationForm): Observable<BaseResponse>

    @POST("authentication/signin")
    fun signIn(@Body form: AuthenticationForm): Observable<AuthenticationResponse>
}