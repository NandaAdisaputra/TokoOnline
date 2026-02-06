package com.nandaadisaputra.tokoonline.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.tokoonline.databinding.ItemProdukBinding
import com.nandaadisaputra.tokoonline.model.Product

class ProductAdapter(list: List<Product> = emptyList()) : RecyclerView.Adapter<ProductAdapter.VH>() {

    // Setter otomatis: tiap kali list diubah, UI langsung refresh
    var list: List<Product> = list
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class VH(val b: ItemProdukBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(ItemProdukBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(h: VH, p: Int) {
        val item = list[p]
        h.b.apply {
            tvNamaProduk.text = item.nama_produk
            tvHarga.text = "Rp ${item.harga}"
            tvStok.text = "Stok: ${item.stok}"
        }
    }
}