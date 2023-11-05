package com.example.fakestore

import android.content.ContentValues
import androidx.room.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query as RetrofitQuery

@Dao
interface ProductsDaoInterface {
    
    
    
    @Query("SELECT*FROM productsfav")
    suspend fun allFavProducts(): List<Products>

    @Insert
    suspend fun addProducts(products: Products)

    @GET("products")
    fun getallProducts(): Call<List<Products>>

    @GET("categories")
    fun getAllCategories(): Call<List<Categories>>

    @GET("products/")
    fun getProductsByCategory(@RetrofitQuery("categoryId") id: Int): Call<List<Products>>

    @GET("products/")
    fun getProductsByTitle(@RetrofitQuery("title") title:String): Call<List<Products>>






}