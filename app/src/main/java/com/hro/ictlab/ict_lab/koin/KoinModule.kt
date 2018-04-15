package com.hro.ictlab.ict_lab.koin

import android.app.Application
import com.hro.ictlab.ict_lab.retrofit.ApiModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module.Module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val koinModule: Module = org.koin.dsl.module.applicationContext {
    bean { api(get()) }
    bean { retrofit(get(), "http://www.nee.com") }
    bean { okHttpClient(get()) }
}

private fun api(retrofit: Retrofit): ApiModule {
    return retrofit.create<ApiModule>(ApiModule::class.java)
}

private fun retrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

private fun okHttpClient(application: Application): OkHttpClient {
    val cache = Cache(application.cacheDir, 10 * 1024 * 1024)
    val builder = OkHttpClient.Builder().cache(cache)

    return builder.build()
}