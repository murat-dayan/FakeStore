package com.example.fakestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fakestore.databinding.ActivityProductsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var pdi: ProductsDaoInterface
    private lateinit var category: Categories

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products)

        binding.lottieView.visibility= View.GONE
        binding.progressBar.visibility= View.VISIBLE
        binding.rvPA.setHasFixedSize(true)
        binding.rvPA.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        category = intent.getSerializableExtra("category") as Categories

        binding.toolbarPA.title = "${category.name}"
        binding.toolbarPA.setBackgroundColor(getColor(R.color.main_color))
        setSupportActionBar(binding.toolbarPA)

        println(category.id)

        allProductsbyCategory()

    }

    fun allProductsbyCategory() {
        pdi = ApiUtils.getProductsDaoInterface()

        pdi.getProductsByCategory(category.id).enqueue(object : Callback<List<Products>> {
            override fun onResponse(
                call: Call<List<Products>>?,
                response: Response<List<Products>>?
            ) {

                if (response != null) {
                    val liste = response.body()

                    productsAdapter = ProductsAdapter(this@ProductsActivity, liste)
                    binding.rvPA.adapter = productsAdapter
                    binding.progressBar.visibility= View.GONE
                    if (liste != null && liste.isEmpty()) {
                        binding.lottieView.visibility= View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<List<Products>>?, t: Throwable) {
                Log.e("onFailure", t.localizedMessage)
            }


        })
    }
}