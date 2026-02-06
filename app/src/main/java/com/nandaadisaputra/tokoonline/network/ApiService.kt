package com.nandaadisaputra.tokoonline.network

import com.nandaadisaputra.tokoonline.model.StoreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("toko.php")
    suspend fun getProducts(@Query("search") q: String? = null): Response<StoreResponse>
}