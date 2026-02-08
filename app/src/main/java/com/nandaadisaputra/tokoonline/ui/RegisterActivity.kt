package com.nandaadisaputra.tokoonline.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaadisaputra.tokoonline.databinding.ActivityRegisterBinding
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah_halaman_bersih
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
        setupObservers()
    }

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                val nama = edtNama.text.toString()
                val user = edtUsername.text.toString()
                val pass = edtPassword.text.toString()

                if (nama.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty()) {
                    // Status: eksekusi_pendaftaran
                    vm.register(user, pass, nama)
                } else {
                    tampilPesan("Status: Harap isi semua kolom")
                }
            }

            tvLogin.setOnClickListener { finish() }
        }
    }

    private fun setupObservers() {
        // Status: memantau_progres_loading
        vm.is_loading.observe(this) {
            binding.pbLoading.isVisible = it
            // Menonaktifkan tombol saat loading agar tidak terjadi double click
            binding.btnRegister.isEnabled = !it
        }

        vm.pesan_status.observe(this) {
            if (it.isNotEmpty()) {
                tampilPesan(it)
                // Reset pesan setelah ditampilkan agar tidak muncul berulang
                vm.pesan_status.value = ""
            }
        }

        vm.register_sukses.observe(this) { sukses ->
            if (sukses == true) {
                tampilPesan("Status: Pendaftaran berhasil, silakan masuk")
                pindah_halaman_bersih(LoginActivity::class.java)
                // Reset status di ViewModel sebelum finish agar tidak memicu observer lagi nanti
                vm.register_sukses.value = false

                finish()
            }
        }
    }

    private fun tampilPesan(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}