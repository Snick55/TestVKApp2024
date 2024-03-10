package com.snick55.testvkapp2024.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.snick55.testvkapp2024.R
import com.snick55.testvkapp2024.core.viewBinding
import com.snick55.testvkapp2024.databinding.FragmentDetailsBinding
import com.snick55.testvkapp2024.domain.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()
    private val binding by viewBinding<FragmentDetailsBinding>()
    private var indexImage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        binding.titleTextView.text = product.title
        binding.descriptionTextView.text = product.description
        binding.ratingBar.rating = product.rating.toFloat()
        binding.priceTextView.text = getString(R.string.price, product.price)
        setupImages(product)
        binding.rightArrow.setOnClickListener {
            indexImage += 1
            setupImages(product)
        }
        binding.leftArrow.setOnClickListener {
            indexImage -= 1
            setupImages(product)
        }

    }


    private fun setupImages(product: Product) {
        binding.rightArrow.visibility =
            if (indexImage == product.images.size - 1) View.GONE else View.VISIBLE
        binding.leftArrow.visibility = if (indexImage == 0) View.GONE else View.VISIBLE
        Glide.with(requireContext())
            .load(product.images[indexImage])
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(binding.images)

    }

}