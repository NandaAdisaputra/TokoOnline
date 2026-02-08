package com.nandaadisaputra.tokoonline.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nandaadisaputra.tokoonline.databinding.ActivityLoginBinding
import com.nandaadisaputra.tokoonline.utils.AppHelper.pindah_halaman_bersih
import com.nandaadisaputra.tokoonline.viewmodel.MainViewModel

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupAction()
        setupObservers()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val user = edtUsername.text.toString()
                val pass = edtPassword.text.toString()

                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    // Status: mengeksekusi_login
                    vm.login(user, pass)
                } else {
                    tampilPesan("Status: Username dan Password harus diisi")
                }
            }

            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun setupObservers() {
        vm.is_loading.observe(this) { loading ->
            binding.pbLoading.isVisible = loading
            binding.btnLogin.isEnabled = !loading
        }

        vm.pesan_status.observe(this) { pesan ->
            if (!pesan.isNullOrEmpty()) {
                tampilPesan(pesan)
                // Status: meriset_pesan_agar_tidak_duplikat
                vm.pesan_status.postValue("")
            }
        }
        vm.login_sukses.observe(this) { data_user ->
            if (data_user != null) {
                // Status: log_validasi_data
                android.util.Log.d("LOGIN_DEBUG", "User ditemukan: ${data_user.nama_lengkap}")

                pindah_halaman_bersih(MainActivity::class.java)

                // Status: riset_status_login
                // Gunakan postValue agar tidak terjadi loop pada Main Thread
                vm.login_sukses.postValue(null)

                // Status: tutup_halaman_login
                finish()
            } else {
                // Status: data_kosong_terdeteksi
                android.util.Log.e("LOGIN_DEBUG", "Login_sukses dipicu tapi data user kosong!")
            }
        }
    }
    private fun tampilPesan(msg: String) {
        if (msg.isNotEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}