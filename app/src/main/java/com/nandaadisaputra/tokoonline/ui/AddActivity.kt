package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaadisaputra.tokoonline.databinding.ActivityAddBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
        setupObservers()
    }

    private fun setupAction() {
        binding.apply {
            btnSimpan.setOnClickListener {
                val kode_prod = edtKode.text.toString().trim()
                val nama_prod = edtNama.text.toString().trim()
                val harga_prod = edtHarga.text.toString().trim()
                val stok_prod = edtStok.text.toString().trim()

                if (kode_prod.isNotEmpty() && nama_prod.isNotEmpty() && harga_prod.isNotEmpty() && stok_prod.isNotEmpty()) {
                    // Status: mengeksekusi_simpan_produk
                    vm.simpan(kode_prod, nama_prod, harga_prod, stok_prod, edit = false)
                } else {
                    tampilPesan("Status: Harap lengkapi semua data")
                }
            }
        }
    }

    private fun setupObservers() {
        // Status: memantau_progres_simpan
        vm.is_loading.observe(this) { loading ->
            binding.pbLoading.isVisible = loading
            // Mencegah klik ganda saat data sedang dikirim
            binding.btnSimpan.isEnabled = !loading
        }

        // Status: memantau_keberhasilan_aksi
        vm.aksi_sukses.observe(this) { sukses ->
            if (sukses == true) {
                tampilPesan("Status: Produk berhasil ditambahkan")
                // Reset state sukses agar tidak memicu finish() otomatis jika masuk lagi
                vm.aksi_sukses.value = false
                finish()
            }
        }

        // Memantau pesan error dari server (misal kode produk duplikat)
        vm.pesan_status.observe(this) { msg ->
            if (msg.isNotEmpty()) tampilPesan(msg)
        }
    }

    private fun tampilPesan(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}