package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.R
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah // status: ambil_fungsi_pindah

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(R.layout.activity_splash_screen)

        // status: tunggu_3_detik_lalu_pindah_dan_finish
        Handler(Looper.getMainLooper()).postDelayed({
            pindah(LoginActivity::class.java, true)
        }, 3000)
    }
}