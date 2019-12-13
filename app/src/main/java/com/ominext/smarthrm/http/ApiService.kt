package com.ominext.smarthrm.http

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/login/{email}")
    fun login(@Path("email") email: String): Observable<Response<Void>>

    @POST("api/login/{email}")
    fun pushinfo(@Path("email") email: String): Observable<Response<Void>>

}