package com.nandaadisaputra.tokoonline.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.nandaadisaputra.tokoonline.adapter.ProductAdapter
import com.nandaadisaputra.tokoonline.databinding.ActivityMainBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    private val adapter by lazy {
        ProductAdapter(mutableListOf(), { onEdit(it) }, { onDelete(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupObservers()
    }

    private fun setupView() {
        binding.apply {
            rvProduk.adapter = adapter

            // Pencarian Otomatis
            edtCari.addTextChangedListener {
                vm.loadData(it.toString())
            }

            fabTambah.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
            }
        }
    }

    private fun setupObservers() {
        // Pantau daftar produk
        vm.list_produk.observe(this) { adapter.updateData(it) }

        // Pantau loading state
        vm.is_loading.observe(this) { binding.pbLoading.isVisible = it }

        // Pantau pesan status (misal: "Berhasil dihapus")
        vm.pesan_status.observe(this) { msg ->
            if (msg.isNotEmpty()) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // PENTING: Jika hapus/tambah/edit sukses, muat ulang data
        vm.aksi_sukses.observe(this) { sukses ->
            if (sukses) {
                vm.loadData(binding.edtCari.text.toString())
            }
        }
    }

    // Refresh data saat kembali ke halaman ini (setelah Tambah/Edit)
    override fun onResume() {
        super.onResume()
        vm.loadData(binding.edtCari.text.toString())
    }

    private fun onEdit(p: com.nandaadisaputra.tokoonline.model.Product) {
        EditActivity.data_produk_edit = p
        startActivity(Intent(this, EditActivity::class.java))
    }

    private fun onDelete(p: com.nandaadisaputra.tokoonline.model.Product) {
        // Tambahkan konfirmasi simpel atau langsung hapus
        vm.hapus(p.kode_produk)
    }
}