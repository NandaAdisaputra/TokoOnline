package com.nandaadisaputra.tokoonline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nandaadisaputra.tokoonline.databinding.ItemProdukBinding
import com.nandaadisaputra.tokoonline.network.Product
import java.text.NumberFormat
import java.util.*

class ProductAdapter(private val klik: (Product) -> Unit) : RecyclerView.Adapter<ProductAdapter.VH>() {

    private var list = emptyList<Product>()

    fun set_data(d: List<Product>) {
        list = d.reversed()
        notifyDataSetChanged()
    }

    inner class VH(val b: ItemProdukBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(
        ItemProdukBinding.inflate(LayoutInflater.from(p.context), p, false)
    )

    override fun onBindViewHolder(h: VH, pos: Int) = h.b.run {
        val p = list[pos]
        tvNamaProduk.text = p.nama_produk
        tvStok.text = "stok: ${p.stok}"
        tvHarga.text = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(p.harga).replace(",00", "")

        root.setOnClickListener { klik(p) }
    }

    override fun getItemCount() = list.size
}