package com.example.fakestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Database
import com.example.fakestore.databinding.ActivityMainBinding
import com.google.gson.ToNumberStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pdi: ProductsDaoInterface
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private var lastQuery: String = ""
    val favoriteList = ArrayList<Products>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)




        binding.toolbarMA.title= getString(R.string.products)
        binding.toolbarMA.setBackgroundColor(getColor(R.color.dark_blue))
        setSupportActionBar(binding.toolbarMA)

        binding.rvMA.setHasFixedSize(true)
        binding.rvMA.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

        binding.rvMAP.setHasFixedSize(true)
        binding.rvMAP.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        allCategories()
        allProducts()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_options_menu,menu)

        val item= menu!!.findItem(R.id.action_search)
        val searchView= item.actionView as SearchView
        searchView.setOnQueryTextListener(this@MainActivity)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_search->{
                return true
            }
            R.id.action_favorites->{
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                return true
            }
            R.id.action_settings->{
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                return true
            }
            R.id.action_exit->{
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            allProducts()
        } else {
            allProductsByTitle(query)
        }
        return true
    }



    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()){
            allProductsByTitle(newText)
        } else {
            allProducts()
        }
        return true
    }

    override fun onBackPressed() {

    }

    fun allProductsByTitle(searchedProduct:String){
        pdi= ApiUtils.getProductsDaoInterface()

        pdi.getProductsByTitle(searchedProduct).enqueue(object : Callback<List<Products>>{
            override fun onResponse(call: Call<List<Products>>?, response: Response<List<Products>>?) {
                if (response != null){
                    val liste= response.body()

                    if (liste != null){
                        productsAdapter= ProductsAdapter(this@MainActivity, liste)
                        binding.rvMAP.adapter= productsAdapter
                    }else{
                        Toast.makeText(this@MainActivity, "Not Found", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<List<Products>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }

        })
    }

    fun
            allCategories(){
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