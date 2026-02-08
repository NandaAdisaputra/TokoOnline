package com.nandaadisaputra.tokoonline.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityLoginBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik
import com.nandaadisaputra.tokoonline.utils.AppHelper.tampil
import com.nandaadisaputra.tokoonline.utils.AppHelper.hilang
import com.nandaadisaputra.tokoonline.utils.AppHelper.set_aktif

class LoginActivity : AppCompatActivity() {
    private val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        // 1. aksi_klik_login
        b.btnLogin.klik {
            val u = b.edtUsername.text.toString()
            val p = b.edtPassword.text.toString()
            if (u.isEmpty() || p.isEmpty()) pesan("lengkapi_data") else vm.login(u, p)
        }

        b.tvRegister.klik { pindah(RegisterActivity::class.java) }

        // 2. pantau_proses (loading -> sukses -> pesan)
        vm.loading.observe(this) { sedang ->
            if (sedang) b.pbLoading.tampil() else b.pbLoading.hilang()
            b.btnLogin.set_aktif(!sedang)
        }

        vm.sukses.observe(this) {
            if (it) {
                vm.sukses.value = false // reset_state
                val i = Intent(this, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(i)
            }
        }

        vm.pesan.observe(this) { if (it.isNotEmpty()) pesan(it) }
    }
}