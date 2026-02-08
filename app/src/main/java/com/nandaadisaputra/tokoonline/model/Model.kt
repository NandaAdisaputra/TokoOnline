package com.nandaadisaputra.tokoonline.model

// status: objek_inti_dengan_nilai_default_opsional
data class Product(
    val kode_produk: String? = null,
    val nama_produk: String? = null,
    val harga: Int = 0,
    val stok: Int = 0
)

data class UserData(val username: String, val nama_lengkap: String)

// status: gunakan_generic_response_untuk_semua_kebutuhan_api
data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val status: String? = null, // status: untuk_handle_status_200_201_atau_ok
    val data: T? = null,        // data: bisa_berisi_List<Product>_atau_UserData
    val user: T? = null         // user: alias_untuk_kompatibilitas_lama
)

// status: typealias_untuk_mempermudah_pembacaan_kode
typealias UserResponse = BaseResponse<UserData>
typealias StoreResponse = BaseResponse<List<Product>>
typealias GenericResponse = BaseResponse<Nothing>