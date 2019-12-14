package com.ominext.smarthrm.http

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ominext.smarthrm.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        const val REQUEST_TIMEOUT = 60L
        const val ISO_8601_DATE_TIME_FORMAT_RECEIVE = "yyyy-MM-dd'T'HH:mm:ssZZ"
        const val BASE_URL = "http://192.168.201.171:8081"
    }

    lateinit var mRetrofit: Retrofit

    fun getClient(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(20, TimeUnit.SECONDS)
        httpClient.addInterceptor(HttpHeaderInterceptor())
        httpClient.connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        val gson =
            GsonBuilder().setDateFormat(ISO_8601_DATE_TIME_FORMAT_RECEIVE).serializeNulls().create()
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
        return mRetrofit
    }
}