package com.example.fakestore

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fakestore.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pdi: ProductsDaoInterface
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var userMail:String
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setTheme(R.style.Theme_FakeStore)

        auth= FirebaseAuth.getInstance()


        binding.toolbarMA.title = getString(R.string.products)
        binding.toolbarMA.setBackgroundColor(getColor(R.color.main_color))
        setSupportActionBar(binding.toolbarMA)

        binding.rvMA.setHasFixedSize(true)
        binding.rvMA.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

        binding.rvMAP.setHasFixedSize(true)
        binding.rvMAP.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        if (!isConnectedToInternet()) {
            Toast.makeText(this, "internet yok", Toast.LENGTH_LONG).show()
        } else {
            allCategories()
            allProducts()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_options_menu, menu)

        val item = menu!!.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this@MainActivity)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_your_cart -> {
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
                return true
            }
            R.id.action_search -> {
                return true
            }
            R.id.action_favorites -> {
                startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                return true
            }
            R.id.action_settings -> {
                val intent= Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_sign_out -> {
                auth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
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
        if (!newText.isNullOrEmpty()) {
            allProductsByTitle(newText)
        } else {
            allProducts()
        }
        return true
    }

    override fun onBackPressed() {

    }

    fun allProductsByTitle(searchedProduct: String) {
        pdi = ApiUtils.getProductsDaoInterface()

        pdi.getProductsByTitle(searchedProduct).enqueue(object : Callback<List<Products>> {
            override fun onResponse(
                call: Call<List<Products>>?,
                response: Response<List<Products>>?
            ) {
                if (response != null) {
                    val liste = response.body()

                    if (liste != null) {
                        productsAdapter = ProductsAdapter(this@MainActivity, liste)
                        binding.rvMAP.adapter = productsAdapter
                    } else {
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
            allCategories() {
        pdi = ApiUtils.getProductsDaoInterface()

        pdi.getAllCategories().enqueue(object : Callback<List<Categories>> {
            override fun onResponse(
                call: Call<List<Categories>>?,
                response: Response<List<Categories>>?
            ) {

                if (response != null) {
                    val liste = response.body()

                    categoriesAdapter = CategoriesAdapter(this@MainActivity, liste)
                    binding.rvMA.adapter = categoriesAdapter


                }

            }

            override fun onFailure(call: Call<List<Categories>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }

    fun allProducts() {
        pdi = ApiUtils.getProductsDaoInterface()

        pdi.getallProducts().enqueue(object : Callback<List<Products>> {
            override fun onResponse(
                call: Call<List<Products>>?,
                response: Response<List<Products>>?
            ) {
                if (response != null) {
                    val liste = response.body()

                    productsAdapter = ProductsAdapter(this@MainActivity, liste)
                    binding.rvMAP.adapter = productsAdapter

                }
            }

            override fun onFailure(call: Call<List<Products>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }

    fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }


}