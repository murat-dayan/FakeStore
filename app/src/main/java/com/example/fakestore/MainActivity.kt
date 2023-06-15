package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fakestore.databinding.ActivityMainBinding
import com.google.gson.ToNumberStrategy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pdi: ProductsDaoInterface
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.toolbarMA.title= "Categories"
        setSupportActionBar(binding.toolbarMA)

        binding.rvMA.setHasFixedSize(true)
        binding.rvMA.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

        binding.rvMAP.setHasFixedSize(true)
        binding.rvMAP.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        allCategories()
        allProducts()


    }



    fun allCategories(){
        pdi= ApiUtils.getProductsDaoInterface()

        pdi.getAllCategories().enqueue(object : Callback<List<Categories>>{
            override fun onResponse(call: Call<List<Categories>>?, response: Response<List<Categories>>?) {

                if (response != null){
                    val liste= response.body()

                    categoriesAdapter= CategoriesAdapter(this@MainActivity, liste)
                    binding.rvMA.adapter= categoriesAdapter
                }

            }

            override fun onFailure(call: Call<List<Categories>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }

    fun allProducts(){
        pdi= ApiUtils.getProductsDaoInterface()

        pdi.getallProducts().enqueue(object : Callback<List<Products>>{
            override fun onResponse(call: Call<List<Products>>?, response: Response<List<Products>>?) {
                if (response != null){
                    val liste= response.body()

                    productsAdapter= ProductsAdapter(this@MainActivity, liste)
                    binding.rvMAP.adapter= productsAdapter

                }
            }

            override fun onFailure(call: Call<List<Products>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }


}