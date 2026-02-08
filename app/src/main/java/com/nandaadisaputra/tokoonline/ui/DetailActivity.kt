package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.tokoonline.databinding.ActivityDetailBinding
import com.nandaadisaputra.tokoonline.network.Product
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik // status: ambil_bantuan_klik

class DetailActivity : AppCompatActivity() {
    private val b by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    companion object {
        var p_detail: Product? = null
    }

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        muat_ui()

        // 1. aksi_edit_pakai_helper
        b.btnEdit.klik {
            EditActivity.p_edit = p_detail
            pindah(EditActivity::class.java)
        }

        // 2. aksi_hapus_dengan_konfirmasi
        b.btnHapus.klik {
            AlertDialog.Builder(this).apply {
                setTitle("hapus_produk")
                setMessage("yakin_hapus?")
                setPositiveButton("ya") { _, _ -> p_detail?.let { vm.hapus(it.kode_produk) } }
                setNegativeButton("batal", null)
            }.show()
        }

        // 3. pantau_perubahan
        vm.pesan.observe(this) {
            if (it.isNotEmpty()) pesan(it)
            if (it == "status: terhapus") {
                p_detail = null
                finish()
            }
        }
    }

    private fun muat_ui() = p_detail?.run {
        b.tvDetailNama.text = nama_produk
        b.tvDetailHarga.text = "Rp $harga"
        b.tvDetailStok.text = "stok: $stok"
    }

    override fun onResume() {
        super.onResume()
        muat_ui() // status: update_tampilan_otomatis_setelah_edit
    }
}