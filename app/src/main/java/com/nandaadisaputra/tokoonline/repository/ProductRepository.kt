package com.nandaadisaputra.tokoonline.repository

import com.nandaadisaputra.tokoonline.utils.AppHelper

class ProductRepository {
    // Mengambil instance API dari Helper agar konsisten
    private val api = AppHelper.api

    // Fungsi fetchAll untuk mengambil semua data
    suspend fun fetchAll() = api.getProducts()

    // Fungsi search untuk pencarian data
    // Dibuat nullable (String?) agar lebih fleksibel
    suspend fun search(q: String?) = api.getProducts(q)
}