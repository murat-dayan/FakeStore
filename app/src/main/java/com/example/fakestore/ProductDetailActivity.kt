package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivityProductDetailBinding
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        val product= intent.getSerializableExtra("product") as Products
        binding.toolbarPDA.title= "${product.title}"
        binding.toolbarPDA.setBackgroundColor(getColor(R.color.dark_blue))

        setSupportActionBar(binding.toolbarPDA)



        Picasso.get()
            .load(product.images[0])
            .into(binding.imageViewProductImage)

        binding.textViewProductName.setText(product.title)
        binding.textViewProductDescription.setText(product.description)




    }
}