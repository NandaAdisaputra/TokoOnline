package com.nandaadisaputra.tokoonline.model

data class StoreResponse(val data: List<Product>)
data class Product(
    val kode_produk: String,
    val nama_produk: String,
    val harga: Int,
    val stok: Int
)