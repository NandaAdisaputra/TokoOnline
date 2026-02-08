package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityEditBinding
import com.nandaadisaputra.tokoonline.network.Product
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik

class EditActivity : AppCompatActivity() {
    private val b by lazy { ActivityEditBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    companion object {
        var p_edit: Product? = null
    }

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        // 1. isi_form_otomatis
        p_edit?.run {
            b.edtKode.setText(kode_produk)
            b.edtKode.isEnabled = false // status: kode_tidak_boleh_diedit
            b.edtNama.setText(nama_produk)
            b.edtHarga.setText(harga.toString())
            b.edtStok.setText(stok.toString())
        }

        // 2. aksi_update_pakai_helper
        b.btnUpdate.klik {
            val k = b.edtKode.text.toString()
            val n = b.edtNama.text.toString()
            val h = b.edtHarga.text.toString()
            val st = b.edtStok.text.toString()

            if (n.isEmpty() || h.isEmpty()) pesan("lengkapi_data")
            else vm.simpan(k, n, h, st, true)
        }

        // 3. pantau_viewmodel
        vm.pesan.observe(this) { if (it.isNotEmpty()) pesan(it) }

        vm.sukses.observe(this) {
            if (it) {
                p_edit = null // status: bersihkan_data_setelah_edit
                finish()
            }
        }
    }
}