package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityAddBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik
import com.nandaadisaputra.tokoonline.utils.AppHelper.set_aktif

class AddActivity : AppCompatActivity() {
    private val b by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        // 1. aksi_simpan_pakai_helper
        b.btnSimpan.klik {
            val k = b.edtKode.text.toString()
            val n = b.edtNama.text.toString()
            val h = b.edtHarga.text.toString()
            val st = b.edtStok.text.toString()

            if (k.isEmpty() || n.isEmpty()) pesan("lengkapi_data")
            else vm.simpan(k, n, h, st, false)
        }

        // 2. pantau_proses (loading -> sukses -> pesan)
        vm.loading.observe(this) { sedang ->
            b.btnSimpan.set_aktif(!sedang)
            b.btnSimpan.text = if (sedang) "proses..." else "simpan"
        }

        vm.sukses.observe(this) { if (it) finish() }

        vm.pesan.observe(this) { if (it.isNotEmpty()) pesan(it) }
    }
}