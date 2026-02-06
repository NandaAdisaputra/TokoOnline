package com.nandaadisaputra.tokoonline

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.nandaadisaputra.tokoonline.adapter.ProductAdapter
import com.nandaadisaputra.tokoonline.databinding.ActivityMainBinding
import com.nandaadisaputra.tokoonline.viewmodel.ProductViewModel
// Import Extension Functions dari AppHelper
import com.nandaadisaputra.tokoonline.utils.AppHelper.showToast
import com.nandaadisaputra.tokoonline.utils.AppHelper.visible


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: ProductViewModel by viewModels()
    private val pAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.adapter = pAdapter

        // Setup Observers
        vm.products.observe(this) { pAdapter.list = it }
        vm.message.observe(this) { showToast(it) }
        vm.isLoading.observe(this) { binding.pb.visible = it } // Menggunakan property 'visible' dari Helper

        vm.load()

        // Setup Search
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?) = true.also { vm.load(q) }
            override fun onQueryTextChange(q: String?) = false
        })
    }
}