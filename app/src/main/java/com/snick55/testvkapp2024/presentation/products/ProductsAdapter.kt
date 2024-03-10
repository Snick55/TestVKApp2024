package com.snick55.testvkapp2024.presentation.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snick55.testvkapp2024.R
import com.snick55.testvkapp2024.databinding.ItemProductBinding
import com.snick55.testvkapp2024.domain.Product

class ProductsAdapter(
    private val onItemClicked: (Product) -> Unit
) : PagingDataAdapter<Product, ProductsAdapter.Holder>(ProductsDiffCallback()),
    View.OnClickListener {

    class Holder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                root.tag = product
                titleTextView.text = product.title
                descriptionTextView.text = product.description

                Glide.with(binding.root.context).load(product.thumbnail)
                    .circleCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(thumbnail)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val product = getItem(position) ?: return
        holder.bind(product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun onClick(v: View) {
        val product = v.tag as Product
        onItemClicked.invoke(product)
    }
}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}