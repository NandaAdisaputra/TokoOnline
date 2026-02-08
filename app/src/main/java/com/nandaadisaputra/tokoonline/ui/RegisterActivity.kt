package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityRegisterBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik
import com.nandaadisaputra.tokoonline.utils.AppHelper.tampil
import com.nandaadisaputra.tokoonline.utils.AppHelper.hilang
import com.nandaadisaputra.tokoonline.utils.AppHelper.set_aktif

class RegisterActivity : AppCompatActivity() {
    private val b by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        // 1. aksi_klik_daftar
        b.btnRegister.klik {
            val n = b.edtNama.text.toString()
            val u = b.edtUsername.text.toString()
            val p = b.edtPassword.text.toString()

            if (n.isEmpty() || u.isEmpty() || p.isEmpty()) pesan("lengkapi_data")
            else vm.register(u, p, n)
        }

        // 2. pantau_proses (loading -> sukses -> pesan)
        vm.loading.observe(this) { sedang_muat ->
            if (sedang_muat) b.pbLoading.tampil() else b.pbLoading.hilang()
            b.btnRegister.set_aktif(!sedang_muat)
        }

        vm.sukses.observe(this) { if (it) { pesan("daftar_berhasil"); finish() } }

        vm.pesan.observe(this) { if (it.isNotEmpty()) pesan(it) }
    }
}