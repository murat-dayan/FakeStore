package com.example.fakestore

import android.content.ContentValues
import androidx.room.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

@Dao
interface ProductsDaoInterface {
    @GET("products")
    fun getallProducts(): Call<List<Products>>

    @GET("categories")
    fun getAllCategories(): Call<List<Categories>>

    @GET("products/")
    fun getProductsByCategory(@Query("categoryId") id: Int): Call<List<Products>>

    @GET("products/")
    fun getProductsByTitle(@Query("title") title:String): Call<List<Products>>






}