package com.nandaadisaputra.tokoonline.repository

import com.nandaadisaputra.tokoonline.utils.AppHelper

class MainRepository {
    private val api = AppHelper.api

    suspend fun login(u: String, p: String) = api.auth("login", u, p)

    suspend fun register(u: String, p: String, n: String) = api.auth("register", u, p, n)

    suspend fun ambil(q: String?) = api.get_produk(q)

    suspend fun hapus(k: String) = api.crud("hapus", k)

    // status: gunakan_if_sebagai_ekspresi_agar_lebih_singkat
    suspend fun simpan(k: String, n: String, h: String, s: String, edit: Boolean) =
        api.crud(if (edit) "edit" else "tambah", k, n, h, s)
}