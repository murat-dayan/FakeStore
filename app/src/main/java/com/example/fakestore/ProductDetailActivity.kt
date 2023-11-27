package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fakestore.databinding.ActivityProductDetailBinding
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        var product= intent.getSerializableExtra("product") as Products

        binding.toolbarPDA.title= "${product.title}"
        binding.toolbarPDA.setBackgroundColor(getColor(R.color.main_color))

        setSupportActionBar(binding.toolbarPDA)



        Picasso.get()
            .load(product.images[0])
            .into(binding.imageViewProductImage)

        binding.textViewProductName.setText(product.title)
        binding.textViewProductDescription.setText(product.description)


        binding.addCartButtonId.setOnClickListener {
            addProductCart(product)
        }

    }

    fun addProductCart(product:Products){

        val db= DatabaseHelper(this)
        val isLoad= SqlDao().addProductCart(    db,
            product,
            product.id,
            product.title,
            product.price,
            product.description,
            product.images[0],
            product.creationAt,
            product.updatedAt,
            product.category.id)

        if (isLoad){
            Toast.makeText(this,R.string.productAdded , Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,R.string.productsAlreadyExist , Toast.LENGTH_SHORT).show()
        }
    }



}