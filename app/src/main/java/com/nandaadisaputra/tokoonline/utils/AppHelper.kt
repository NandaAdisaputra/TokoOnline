package com.nandaadisaputra.tokoonline.utils

import android.app.Activity
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

    // status: bantuan_toast_dan_pindah_halaman
    fun Activity.pesan(m: String) = Toast.makeText(this, m, Toast.LENGTH_SHORT).show()

    fun Activity.pindah(t: Class<*>, h: Boolean = false) {
        startActivity(Intent(this, t))
        if (h) finish()
    }

    // status: bantuan_klik_lebih_ringkas
    fun View.klik(aksi: () -> Unit) = setOnClickListener { aksi() }

    // status: bantuan_tampil_hilang_view
    fun View.tampil() { isVisible = true }
    fun View.hilang() { isVisible = false }
    fun View.set_aktif(status: Boolean) { isEnabled = status }
}