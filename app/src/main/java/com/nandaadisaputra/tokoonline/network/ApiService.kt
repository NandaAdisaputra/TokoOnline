package com.nandaadisaputra.tokoonline.network

import com.nandaadisaputra.tokoonline.model.GenericResponse
import com.nandaadisaputra.tokoonline.model.StoreResponse
import com.nandaadisaputra.tokoonline.model.UserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded @POST("auth.php")
    suspend fun login(@Field("aksi") a: String = "login", @Field("user") u: String, @Field("pass") p: String): Response<UserResponse>

    @FormUrlEncoded @POST("auth.php")
    suspend fun register(@Field("aksi") a: String = "register", @Field("user") u: String, @Field("pass") p: String, @Field("nama") n: String): Response<GenericResponse>

    @GET("toko.php")
    suspend fun getProducts(@Query("cari") q: String? = null): Response<StoreResponse>

    @FormUrlEncoded @POST("toko.php")
    suspend fun addProduct(@Field("aksi") a: String = "tambah", @Field("kode") k: String, @Field("nama") n: String, @Field("harga") h: String, @Field("stok") s: String): Response<GenericResponse>

    @FormUrlEncoded @POST("toko.php")
    suspend fun updateProduct(@Field("aksi") a: String = "update", @Field("kode") k: String, @Field("nama") n: String, @Field("harga") h: String, @Field("stok") s: String): Response<GenericResponse>

    @FormUrlEncoded @POST("toko.php")
    suspend fun deleteProduct(@Field("aksi") a: String = "hapus", @Field("kode") k: String): Response<GenericResponse>
}