package com.sunitawebapp.admin_giriexp.retrofit

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sunitawebapp.employee_giriexp.utils.ApiLinks
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    private var retrofit = Retrofit.Builder()
        .baseUrl(ApiLinks.ROOT_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass)
    }
}
