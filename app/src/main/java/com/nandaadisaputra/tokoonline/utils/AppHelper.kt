package com.nandaadisaputra.tokoonline.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nandaadisaputra.tokoonline.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.Locale

object AppHelper {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/api_toko/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    fun Int.toRupiah() = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(this).replace(",00", "")

    // Status: template_navigasi_antar_halaman
    fun Context.pindah_halaman(tujuan: Class<*>, data: Bundle? = null, selesaikan: Boolean = false) {
        val intent_objek = Intent(this, tujuan)
        data?.let {
            intent_objek.putExtras(it)
        }
        startActivity(intent_objek)

        // Status: mengecek_apakah_activity_sekarang_harus_ditutup
        if (selesaikan && this is Activity) {
            this.finish()
        }
    }

    // Status: template_navigasi_bersih_stack (khusus Login/Logout)
    fun Context.pindah_halaman_bersih(tujuan: Class<*>) {
        val intent_objek = Intent(this, tujuan).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent_objek)
        if (this is Activity) {
            this.finish()
        }
    }
}