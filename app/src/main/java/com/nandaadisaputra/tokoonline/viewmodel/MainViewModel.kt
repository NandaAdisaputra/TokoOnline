package com.nandaadisaputra.tokoonline.viewmodel

import androidx.lifecycle.*
import com.nandaadisaputra.tokoonline.model.*
import com.nandaadisaputra.tokoonline.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = MainRepository()

    val list_produk = MutableLiveData<List<Product>>()
    val is_loading = MutableLiveData<Boolean>()
    val pesan_status = MutableLiveData<String>()
    val login_sukses = MutableLiveData<UserData?>()
    val register_sukses = MutableLiveData<Boolean>()
    val aksi_sukses = MutableLiveData<Boolean>()

    // --- AUTH: Login ---
    fun login(u: String, p: String) = viewModelScope.launch {
        is_loading.value = true
        try {
            val res = repo.login(u, p)
            val response_body = res.body()

            if (res.isSuccessful && response_body != null) {
                if (response_body.success == true && response_body.user != null) {
                    // Pastikan data user dikirim hanya jika tidak null
                    login_sukses.postValue(response_body.user)
                } else {
                    // Ambil pesan dari server, jika tidak ada pakai default
                    val pesan_error = response_body.message ?: "Username atau Password salah"
                    pesan_status.postValue("Status: $pesan_error")
                }
            } else {
                pesan_status.postValue("Status: Gagal terhubung ke server (Error ${res.code()})")
            }
        } catch (e: Exception) {
            // Logging error ke Logcat untuk mempermudah Anda mencari penyebab pastinya
            android.util.Log.e("LOGIN_ERROR", "Penyebab: ${e.message}")
            pesan_status.postValue("Status: Koneksi Gagal atau Masalah Server")
        } finally {
            is_loading.value = false
        }
    }
    // --- AUTH: Register (Tambahan agar sesuai RegisterActivity) ---
    fun register(u: String, p: String, n: String) = viewModelScope.launch {
        register_sukses.value = false // Reset status sebelum mulai
        is_loading.value = true
        try {
            val res = repo.register(u, p, n)
            if (res.isSuccessful && res.body()?.success == true) {
                register_sukses.postValue(true)
            } else {
                // Jika gagal, pastikan tetap false
                register_sukses.postValue(false)
                pesan_status.postValue("Status: ${res.body()?.message ?: "Gagal Daftar"}")
            }
        } catch (e: Exception) {
            register_sukses.postValue(false)
            pesan_status.postValue("Status: Gagal terhubung ke server")
        } finally {
            is_loading.value = false
        }
    }
    // --- PRODUK: Read & Search ---
    fun loadData(q: String? = null) = viewModelScope.launch {
        is_loading.value = true
        try {
            val res = repo.getProd(q)
            if (res.isSuccessful) list_produk.postValue(res.body()?.data)
        } finally {
            is_loading.value = false
        }
    }

    // --- PRODUK: Create & Update ---
    fun simpan(k: String, n: String, h: String, s: String, edit: Boolean) = viewModelScope.launch {
        try {
            val res = repo.save(k, n, h, s, edit)
            if (res.isSuccessful && res.body()?.success == true) {
                aksi_sukses.postValue(true)
                pesan_status.postValue("Status: Berhasil disimpan")
            }
        } catch (e: Exception) {
            pesan_status.postValue("Status: Gagal menyimpan data")
        }
    }

    // --- PRODUK: Delete ---
    fun hapus(k: String) = viewModelScope.launch {
        try {
            val res = repo.delete(k)
            if (res.isSuccessful) {
                aksi_sukses.postValue(true)
                pesan_status.postValue("Status: Berhasil dihapus")
            }
        } catch (e: Exception) {
            pesan_status.postValue("Status: Gagal menghapus data")
        }
    }
}