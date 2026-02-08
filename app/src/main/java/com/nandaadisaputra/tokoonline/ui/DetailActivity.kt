package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityDetailBinding
import com.nandaadisaputra.tokoonline.model.Product
import com.nandaadisaputra.tokoonline.utils.AppHelper.toRupiah
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    // Status: Variabel statis untuk menerima data dari MainActivity tanpa Parcelize
    companion object {
        var data_produk_detail: Product? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupObservers()
    }

    private fun setupView() {
        // Status: Menampilkan data produk yang dipilih
        data_produk_detail?.let {
            binding.apply {
                tvDetailKode.text = "Kode: ${it.kode_produk}"
                tvDetailNama.text = it.nama_produk
                tvDetailHarga.text = it.harga.toRupiah()
                tvDetailStok.text = "Persediaan: ${it.stok} unit"
            }
        }
    }

    private fun setupAction() {
        binding.btnHapus.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus produk ini?")
                .setPositiveButton("Ya") { _, _ ->
                    data_produk_detail?.let { vm.hapus(it.kode_produk) }
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        binding.btnEdit.setOnClickListener {
            // Status: Mengirim data ke EditActivity sebelum berpindah
            EditActivity.data_produk_edit = data_produk_detail
            startActivity(android.content.Intent(this, EditActivity::class.java))
        }
    }

    private fun setupObservers() {
        vm.aksi_sukses.observe(this) { sukses ->
            if (sukses) finish() // Status: Kembali ke daftar jika berhasil hapus
        }
    }
}