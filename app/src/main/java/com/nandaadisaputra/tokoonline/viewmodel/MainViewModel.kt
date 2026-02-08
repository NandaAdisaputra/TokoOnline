package com.nandaadisaputra.tokoonline.viewmodel

import androidx.lifecycle.*
import com.nandaadisaputra.tokoonline.network.Product
import com.nandaadisaputra.tokoonline.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = MainRepository()
    val list_produk = MutableLiveData<List<Product>>()
    val loading = MutableLiveData<Boolean>()
    val pesan = MutableLiveData<String>()
    val sukses = MutableLiveData<Boolean>()

    // status: pola_inti_eksekusi
    private fun eksekusi(blok: suspend () -> Unit) = viewModelScope.launch {
        loading.value = true
        try { blok() } catch (e: Exception) { pesan.value = "status: koneksi_error" }
        finally { loading.value = false }
    }

    // status: gunakan_body_langsung_untuk_kecepatan
    fun muat_data(q: String? = null) = eksekusi {
        list_produk.value = repo.ambil(q).body()?.data ?: emptyList()
    }

    fun login(u: String, p: String) = eksekusi {
        val res = repo.login(u, p)
        val ok = res.isSuccessful && (res.body()?.status in listOf("ok", "200"))
        sukses.value = ok
        if (!ok) pesan.value = "status: ${res.body()?.message ?: "gagal_login"}"
    }

    fun register(u: String, p: String, n: String) = eksekusi {
        val res = repo.register(u, p, n)
        sukses.value = res.isSuccessful && (res.body()?.status in listOf("ok", "201"))
        if (sukses.value != true) pesan.value = "status: ${res.body()?.message ?: "gagal_daftar"}"
    }

    fun simpan(k: String, n: String, h: String, s: String, edit: Boolean) = eksekusi {
        val res = repo.simpan(k, n, h, s, edit)
        val ok = res.isSuccessful && res.body()?.status in listOf("200", "201")
        sukses.value = ok
        pesan.value = if (ok) "status: berhasil_simpan" else "status: ${res.body()?.message ?: "gagal"}"
    }

    fun hapus(k: String) = eksekusi {
        val res = repo.hapus(k)
        if (res.isSuccessful && res.body()?.status == "200") {
            pesan.value = "status: terhapus"
            muat_data()
        } else pesan.value = "status: gagal_hapus"
    }
}