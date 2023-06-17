package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fakestore.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var dbh: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_favorite)


        binding.toolbarFA.title= getString(R.string.favorites)
        binding.toolbarFA.setBackgroundColor(getColor(R.color.dark_blue))

        setSupportActionBar(binding.toolbarFA)

        binding.rvFA.setHasFixedSize(true)
        binding.rvFA.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        dbh= DatabaseHelper(this@FavoriteActivity)

        val productsList= SqlDao().getProducts(dbh)

        productsAdapter= ProductsAdapter(this@FavoriteActivity, productsList)
        binding.rvFA.adapter= productsAdapter





    }




}