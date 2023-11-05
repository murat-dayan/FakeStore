package com.example.fakestore

import android.content.ContentValues

class SqlDao {

    fun addProduct(dbh: DatabaseHelper,
                   product:Products,
                    id:Int,
                    title:String,
                    price:Int,
                    description: String,
                    images:String,
                    creationAt:String,
                    updatedAt:String,
                    category:Int) : Boolean{

        val db= dbh.writableDatabase

        val cursor = db.query(
            "products",
            arrayOf("id"),
            "id=?",
            arrayOf(product.id.toString()),
            null,
            null,
            null
        )

        // If a record with the same ID exists, return false and do not insert the new record
        if (cursor.count > 0) {
            cursor.close()
            return false
        }

        val values= ContentValues()
        values.put("id",id)
        values.put("title",title)
        values.put("price",price)
        values.put("description",description)
        values.put("images",images)
        values.put("creationAt",creationAt)
        values.put("updatedAt",updatedAt)
        values.put("category",category)

        db.insertOrThrow("products", null,values)
        db.close()
        return true

    }

    fun addProductCart(dbh: DatabaseHelper,
                   product:Products,
                   id:Int,
                   title:String,
                   price:Int,
                   description: String,
                   images:String,
                   creationAt:String,
                   updatedAt:String,
                   category:Int) : Boolean{

        val db= dbh.writableDatabase

        val cursor = db.query(
            "productsCart",
            arrayOf("id"),
            "id=?",
            arrayOf(product.id.toString()),
            null,
            null,
            null
        )

        // If a record with the same ID exists, return false and do not insert the new record
        if (cursor.count > 0) {
            cursor.close()
            return false
        }

        val values= ContentValues()
        values.put("id",id)
        values.put("title",title)
        values.put("price",price)
        values.put("description",description)
        values.put("images",images)
        values.put("creationAt",creationAt)
        values.put("updatedAt",updatedAt)
        values.put("category",category)

        db.insertOrThrow("productsCart", null,values)
        db.close()
        return true

    }


    fun getProducts(dbh:DatabaseHelper) : ArrayList<Products>{
        val productsArrayList= ArrayList<Products>()

        val db= dbh.writableDatabase

        val cursor= db.rawQuery("SELECT*FROM products", null)

        while (cursor.moveToNext()){

            val imageList= listOf<String>(cursor.getString(cursor.getColumnIndexOrThrow("images")))

            val category= Categories(cursor.getInt(cursor.getColumnIndexOrThrow("category")),
                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                cursor.getString(cursor.getColumnIndexOrThrow("images")),
                cursor.getString(cursor.getColumnIndexOrThrow("creationAt")),
                cursor.getString(cursor.getColumnIndexOrThrow("updatedAt"))
                )

            val product= Products(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
            cursor.getString(cursor.getColumnIndexOrThrow("title")),
            cursor.getInt(cursor.getColumnIndexOrThrow("price")),
            cursor.getString(cursor.getColumnIndexOrThrow("description")),
            imageList,
            cursor.getString(cursor.getColumnIndexOrThrow("creationAt")),
            cursor.getString(cursor.getColumnIndexOrThrow("updatedAt")),
            category
            )

            productsArrayList.add(product)
        }
        return productsArrayList
    }

    fun getProductsCart(dbh:DatabaseHelper) : ArrayList<Products>{
        val productsArrayList= ArrayList<Products>()

        val db= dbh.writableDatabase

        val cursor= db.rawQuery("SELECT*FROM productsCart", null)

        while (cursor.moveToNext()){

            val imageList= listOf<String>(cursor.getString(cursor.getColumnIndexOrThrow("images")))

            val category= Categories(cursor.getInt(cursor.getColumnIndexOrThrow("category")),
                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                cursor.getString(cursor.getColumnIndexOrThrow("images")),
                cursor.getString(cursor.getColumnIndexOrThrow("creationAt")),
                cursor.getString(cursor.getColumnIndexOrThrow("updatedAt"))
            )

            val product= Products(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("title")),
                cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                imageList,
                cursor.getString(cursor.getColumnIndexOrThrow("creationAt")),
                cursor.getString(cursor.getColumnIndexOrThrow("updatedAt")),
                category
            )

            productsArrayList.add(product)
        }
        return productsArrayList
    }

    fun deleteProductsCart(dbh: DatabaseHelper, id:Int){
        val db= dbh.writableDatabase
        db.delete("productsCart","id=?", arrayOf(id.toString()))
        db.close()
    }



}