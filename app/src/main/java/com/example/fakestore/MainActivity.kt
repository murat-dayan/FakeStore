package com.example.fakestore

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fakestore.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pdi: ProductsDaoInterface
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var dbh: DatabaseHelper
    private lateinit var list: List<Products>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setTheme(R.style.Theme_FakeStore)


        list = ArrayList<Products>()

        auth = FirebaseAuth.getInstance()

        binding.progressBar.visibility = View.VISIBLE


        binding.toolbarMA.title = getString(R.string.app_name)
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
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_sign_out -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("FAKESTORE")
                builder.setMessage("Favori ve Sepetinizdek ürünleriniz kaybolacaktır. Çıkış Yapmak İstediğinize emin misiniz?")
                builder.setPositiveButton("Çıkış Yap") { dialog, which ->
                    dbh = DatabaseHelper(this)
                    SqlDao().deleteProducts(dbh)
                    SqlDao().deleteCarts(dbh)
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                builder.setNegativeButton("İptal") { dialog, which ->
                }
                val dialog = builder.create()
                dialog.show()


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


    fun allProductsByTitle(searchedProduct: String) {
        pdi = ApiUtils.getProductsDaoInterface()

        pdi.getProductsByTitle(searchedProduct).enqueue(object : Callback<List<Products>> {
            override fun onResponse(
                call: Call<List<Products>>?,
                response: Response<List<Products>>?
            ) {
                if (response != null) {
                    list = response.body()
                    productsAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<List<Products>>?, t: Throwable) {
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
                    list = response.body()
                    if (list.isNotEmpty()) {
                        if (!::productsAdapter.isInitialized) {
                            productsAdapter = ProductsAdapter(this@MainActivity, list)
                            binding.rvMAP.adapter = productsAdapter
                        } else {
                            productsAdapter.notifyDataSetChanged()
                        }
                        binding.progressBar.visibility = View.GONE
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

    fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    fun allCategories() {
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
                    binding.progressBar.visibility = View.GONE

                }

            }

            override fun onFailure(call: Call<List<Categories>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }


}