package com.nandaadisaputra.tokoonline.repository

import com.nandaadisaputra.tokoonline.utils.AppHelper

class MainRepository {
    private val api = AppHelper.api

    suspend fun login(u: String, p: String) = api.login(u = u, p = p)
    suspend fun register(u: String, p: String, n: String) = api.register(u = u, p = p, n = n)
    suspend fun getProd(q: String?) = api.getProducts(q)
    suspend fun delete(k: String) = api.deleteProduct(k = k)
    suspend fun save(k: String, n: String, h: String, s: String, edit: Boolean) =
        if (edit) api.updateProduct(k=k, n=n, h=h, s=s) else api.addProduct(k=k, n=n, h=h, s=s)
}