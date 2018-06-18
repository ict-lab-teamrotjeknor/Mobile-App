package com.hro.ictlab.ict_lab.koin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.webkit.CookieManager
import com.hro.ictlab.ict_lab.base.BaseActivity.Companion.SESSION_COOKIE
import com.hro.ictlab.ict_lab.retrofit.ApiModule
import okhttp3.*
import org.koin.dsl.module.Module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Cookie



val koinModule: Module = org.koin.dsl.module.applicationContext {
    bean { api(get()) }
    bean { retrofit(get(), "http://145.24.222.103:8080/") }
    bean { okHttpClient(get()) }
    bean { sharedPrefs(get()) }
}

private fun api(retrofit: Retrofit): ApiModule {
    return retrofit.create<ApiModule>(ApiModule::class.java)
}

private fun retrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

private fun okHttpClient(application: Application): OkHttpClient {

    val interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        var request = chain.request()

        val builder = request.newBuilder().addHeader("Set-Cookie", sharedPrefs(application).getString(SESSION_COOKIE, ""))

        if (response.headers("Set-Cookie").isNotEmpty()) {
            sharedPrefs(application).edit().putString(SESSION_COOKIE, response.header("Set-Cookie").substringAfter("=").substringBefore(";")).apply()
        }

        request = builder.build()
        chain.proceed(request)
    }

    return OkHttpClient.Builder().addInterceptor(interceptor).build()
}

fun sharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}