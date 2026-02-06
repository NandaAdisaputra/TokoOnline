package com.nandaadisaputra.tokoonline.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.nandaadisaputra.tokoonline.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppHelper {
    private const val BASE_URL = "http://10.0.2.2/api_toko/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Di dalam object AppHelper
    fun Context.showToast(msg: String?) {
        msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }
    inline fun <reified T> Context.move() = startActivity(Intent(this, T::class.java))

    // Versi lebih modern menggunakan KTX properti
    var View.visible: Boolean
        get() = isVisible
        set(value) { isVisible = value }
}