package com.ominext.smarthrm.http

import com.ominext.smarthrm.model.History
import com.ominext.smarthrm.model.OAuthTokenEntity
import com.ominext.smarthrm.model.ScanQR
import com.ominext.smarthrm.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/api/auth/token")
    fun login(@Body user: User): Observable<Response<OAuthTokenEntity>>

    @POST("api/user/scan/qrCode")
    fun pushinfo(@Body scanQR: ScanQR): Observable<Response<Void>>

    @GET("api/getHistory")
    fun getHistory():Observable<Response<ArrayList<History>>>
}