package com.example.fakestore

class ApiUtils {

    companion object{

        val BASE_URL= "https://api.escuelajs.co/api/v1/"

        fun getProductsDaoInterface() : ProductsDaoInterface{

            return  RetrofitClient.getClient(BASE_URL).create(ProductsDaoInterface::class.java)

        }

    }

}