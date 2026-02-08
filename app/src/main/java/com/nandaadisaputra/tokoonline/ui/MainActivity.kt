package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.nandaadisaputra.tokoonline.adapter.ProductAdapter
import com.nandaadisaputra.tokoonline.databinding.ActivityMainBinding
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel
import com.nandaadisaputra.tokoonline.model.Product // status: pastikan_merujuk_ke_model
import com.nandaadisaputra.tokoonline.utils.AppHelper.pesan
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah
import com.nandaadisaputra.tokoonline.utils.AppHelper.klik
import com.nandaadisaputra.tokoonline.utils.AppHelper.tampil
import com.nandaadisaputra.tokoonline.utils.AppHelper.hilang

class MainActivity : AppCompatActivity() {
    private val b by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    private val adp by lazy { ProductAdapter {
        DetailActivity.p_detail = it
        pindah(DetailActivity::class.java)
    }}

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        setContentView(b.root)

        b.rvProduk.adapter = adp
        b.edtCari.addTextChangedListener { vm.muat_data(it.toString()) }
        b.fabTambah.klik { pindah(AddActivity::class.java) }

        vm.list_produk.observe(this) { it?.let { adp.set_data(it) } }
        vm.loading.observe(this) { if (it) b.pbLoading.tampil() else b.pbLoading.hilang() }
        vm.pesan.observe(this) { if (it.isNotEmpty()) pesan(it) }
    }

    override fun onResume() {
        super.onResume()
        vm.muat_data()
    }
}