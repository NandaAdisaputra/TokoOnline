package com.nandaadisaputra.tokoonline.network

import retrofit2.Response
import retrofit2.http.*

// status: model_data_inti
data class Product(val kode_produk: String, val nama_produk: String, val harga: Int, val stok: Int)
data class User(val username: String, val nama_lengkap: String)

// status: satu_respon_untuk_semua_kebutuhan
data class ResponAPI(
    val status: String? = null,
    val success: Boolean? = null,
    val message: String,
    val user: User? = null,
    val data: List<Product>? = null
)

interface ApiService {
    @FormUrlEncoded
    @POST("auth.php")
    suspend fun auth(
        @Field("aksi") a: String,
        @Field("user") u: String,
        @Field("pass") p: String,
        @Field("nama") n: String? = null
    ): Response<ResponAPI>

    @GET("toko.php")
    suspend fun get_produk(@Query("cari") q: String? = null): Response<ResponAPI>

    @FormUrlEncoded
    @POST("toko.php")
    suspend fun crud(
        @Field("aksi") a: String,
        @Field("kode_produk") k: String,
        @Field("nama_produk") n: String? = null,
        @Field("harga") h: String? = null,
        @Field("stok") s: String? = null
    ): Response<ResponAPI>
}