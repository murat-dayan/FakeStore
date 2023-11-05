package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity(), CartsAdapter.ProductAdapterListener {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartsAdapter: CartsAdapter
    private lateinit var dbh:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_cart)

        binding.toolbarCA.title= getString(R.string.yourCart)
        binding.toolbarCA.setBackgroundColor(getColor(R.color.dark_blue))
        setSupportActionBar(binding.toolbarCA)


        binding.rvCA.setHasFixedSize(true)
        binding.rvCA.layoutManager= LinearLayoutManager(this)

        dbh= DatabaseHelper(this@CartActivity)

        val productsCartList= SqlDao().getProductsCart(dbh)

        cartsAdapter= CartsAdapter(this@CartActivity, productsCartList,this)
        cartsAdapter.updateTotalPrice()
        binding.rvCA.adapter= cartsAdapter


    }

    override fun onNumberOfProductTotalPriceChanged(numberOfProductTotalprice: Int) {
        binding.cartAmountId.text= "${resources.getString(R.string.cartAmount)}${numberOfProductTotalprice}"
    }


}