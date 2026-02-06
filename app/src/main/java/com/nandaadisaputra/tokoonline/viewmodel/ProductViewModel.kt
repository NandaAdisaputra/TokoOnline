package com.nandaadisaputra.tokoonline.viewmodel

import androidx.lifecycle.*
import com.nandaadisaputra.tokoonline.model.Product
import com.nandaadisaputra.tokoonline.utils.AppHelper
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    val products = MutableLiveData<List<Product>>()
    val isLoading = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    fun load(q: String? = null) = viewModelScope.launch {
        isLoading.value = true
        runCatching { AppHelper.api.getProducts(q) }
            .onSuccess { res ->
                if (res.isSuccessful) {
                    products.value = res.body()?.data ?: emptyList()
                    message.value = "Status: Data berhasil dimuat"
                }
            }
            .onFailure { message.value = "Status: Koneksi bermasalah" }
        isLoading.value = false
    }
}