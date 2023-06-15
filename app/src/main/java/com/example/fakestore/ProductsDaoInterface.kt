package com.example.fakestore

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsDaoInterface {

    @GET("products")
    fun getallProducts(): Call<List<Products>>

    @GET("categories")
    fun getAllCategories(): Call<List<Categories>>

    @GET("products/")
    fun getProductsByCategory(@Query("categoryId") id: Int): Call<List<Products>>

}