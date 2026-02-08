package com.nandaadisaputra.tokoonline.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.R

class SplashScreenActivity : AppCompatActivity() {

    // Properti menggunakan format lowercase_underscore
    private val splash_time_out: Long = 3000
    private val handler_splash = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Menjalankan timer perpindahan halaman
        handler_splash.postDelayed({
            val intent_login = Intent(this, LoginActivity::class.java)
            startActivity(intent_login)
            finish()
        }, splash_time_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Status: membersihkan_handler
        // Menghapus callback agar tidak terjadi kebocoran memori jika activity dihancurkan sebelum 3 detik
        handler_splash.removeCallbacksAndMessages(null)
    }
}