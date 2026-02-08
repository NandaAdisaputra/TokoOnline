package com.nandaadisaputra.tokoonline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.tokoonline.databinding.ItemProdukBinding
import com.nandaadisaputra.tokoonline.model.Product
import com.nandaadisaputra.tokoonline.utils.AppHelper.toRupiah

class ProductAdapter(
    private var list: MutableList<Product>,
    private val onClick: (Product) -> Unit, // Diubah menjadi onClick untuk Detail
    private val onDelete: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    // Menggunakan format camelCase untuk binding di dalam ViewHolder
    inner class VH(val binding: ItemProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val produk_item = list[position]
        holder.binding.apply {
            // Sinkronisasi data ke View
            tvNamaProduk.text = produk_item.nama_produk
            tvHarga.text = produk_item.harga.toRupiah()
            tvStok.text = "Stok: ${produk_item.stok}"

            // Status: klik_pendek untuk melihat detail/edit
            root.setOnClickListener { onClick(produk_item) }

            // Status: klik_lama untuk menghapus data
            root.setOnLongClickListener {
                onDelete(produk_item)
                true
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateData(new_list: List<Product>?) {
        list.clear()
        if (new_list != null) {
            list.addAll(new_list)
        }
        // Memberitahu adapter bahwa data telah berubah (Status: refresh_list)
        notifyDataSetChanged()
    }
}