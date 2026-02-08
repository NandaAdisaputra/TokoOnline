package com.nandaadisaputra.tokoonline.model

// Objek Utama
data class Product(
    val kode_produk: String,
    val nama_produk: String,
    val harga: Int,
    val stok: Int
)

data class UserData(val username: String, val nama_lengkap: String)

// Respon API
data class UserResponse(val success: Boolean, val message: String, val user: UserData?)
data class StoreResponse(val data: List<Product>)
data class GenericResponse(val success: Boolean, val message: String)