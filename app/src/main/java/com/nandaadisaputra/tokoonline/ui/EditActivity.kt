package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaadisaputra.tokoonline.databinding.ActivityEditBinding
import com.nandaadisaputra.tokoonline.model.Product
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class EditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    companion object {
        // Menggunakan format lowercase_underscore sesuai preferensi Anda
        var data_produk_edit: Product? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupObservers()
    }

    private fun setupView() {
        // Status: mengisi_data_ke_komponen_view
        data_produk_edit?.let {
            binding.apply {
                edtKode.setText(it.kode_produk)
                edtKode.isEnabled = false // Kode primer bersifat unik, tidak boleh diubah
                edtNama.setText(it.nama_produk)
                edtHarga.setText(it.harga.toString())
                edtStok.setText(it.stok.toString())
            }
        } ?: run {
            tampilPesan("Status: Data produk tidak ditemukan")
            finish()
        }
    }

    private fun setupAction() {
        binding.apply {
            btnUpdate.setOnClickListener {
                val k = edtKode.text.toString()
                val n = edtNama.text.toString()
                val h = edtHarga.text.toString()
                val s = edtStok.text.toString()

                if (n.isNotEmpty() && h.isNotEmpty() && s.isNotEmpty()) {
                    // Status: mengirim_pembaruan_ke_server
                    vm.simpan(k, n, h, s, edit = true)
                } else {
                    tampilPesan("Status: Harap lengkapi semua data")
                }
            }
        }
    }

    private fun setupObservers() {
        // Status: memantau_keberhasilan_aksi
        vm.aksi_sukses.observe(this) { sukses ->
            if (sukses) {
                tampilPesan("Status: Perubahan berhasil disimpan")
                finish()
            }
        }

        vm.is_loading.observe(this) { loading ->
            binding.pbLoading.isVisible = loading
            binding.btnUpdate.isEnabled = !loading
        }

        vm.pesan_status.observe(this) { msg ->
            if (msg.isNotEmpty()) tampilPesan(msg)
        }
    }

    private fun tampilPesan(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        // PENTING: Membersihkan data statis agar tidak terjadi kebocoran memori (memory leak)
        // Status: membersihkan_data_produk
        data_produk_edit = null
    }
}