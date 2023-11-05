package com.example.fakestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CartsAdapter(val mContext:Context, var productsList: List<Products>, val productAdapterListener: ProductAdapterListener) :
    RecyclerView.Adapter<CartsAdapter.CardviewHolder>() {

    private var totalPrice: Int=0

    fun updateTotalPrice() {
        totalPrice = 0
        for (product in productsList) {
            totalPrice += product.price
        }
        notifyDataSetChanged()
        productAdapterListener.onNumberOfProductTotalPriceChanged(totalPrice)
    }


    private lateinit var db: DatabaseHelper

    inner class CardviewHolder(view:View): RecyclerView.ViewHolder(view){

        var cardviewCart:CardView
        var cardviewProductImage: ImageView
        var cardviewProductName: TextView
        var cardviewProductPrice: TextView
        var cardviewButtonIncrease: FloatingActionButton
        var cardviewButtonDecrease: FloatingActionButton
        var cardviewProductNumber: TextView
        var cardviewDeleteImage: ImageView

        init {
            cardviewCart= view.findViewById(R.id.cardview_cart_id)
            cardviewProductImage= view.findViewById(R.id.cardview_imageview_id)
            cardviewProductName= view.findViewById(R.id.cardview_product_name_textview_id)
            cardviewProductPrice= view.findViewById(R.id.cardview_product_price_textview_id)
            cardviewButtonIncrease= view.findViewById(R.id.cardview_increase_button_id)
            cardviewButtonDecrease= view.findViewById(R.id.cardview_decrease_button_id)
            cardviewProductNumber= view.findViewById(R.id.cardview_product_number_textview_id)
            cardviewDeleteImage= view.findViewById(R.id.cardview_delete_imageview_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardviewHolder {
        val design= LayoutInflater.from(mContext).inflate(R.layout.cardview_carts,parent,false)
        return CardviewHolder(design)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: CardviewHolder, position: Int) {

        val product = productsList.get(position)
        var numberOfProduct:Int = 1

        holder.cardviewProductNumber.text = "${numberOfProduct}"


        holder.cardviewButtonIncrease.setOnClickListener {
            numberOfProduct = numberOfProduct + 1
            holder.cardviewProductNumber.text = "${numberOfProduct}"
            if (numberOfProduct>1){
                totalPrice += product.price
                productAdapterListener.onNumberOfProductTotalPriceChanged(totalPrice)
            }
        }
        holder.cardviewButtonDecrease.setOnClickListener {
            if (numberOfProduct > 1) {
                numberOfProduct = numberOfProduct - 1
                holder.cardviewProductNumber.text = "${numberOfProduct}"
                totalPrice -= product.price
                productAdapterListener.onNumberOfProductTotalPriceChanged(totalPrice)
            }
        }


        holder.cardviewProductName.text = product.title
        holder.cardviewProductPrice.text = "€${product.price}"

        holder.cardviewDeleteImage.setOnClickListener {


            Snackbar.make(
                holder.cardviewProductImage,
                "${product.title} ${mContext.resources.getString(R.string.isProductDelete)}",
                Snackbar.LENGTH_LONG
            )
                .setAction(mContext.resources.getString(R.string.yes)) { y ->
                    db = DatabaseHelper(mContext)
                    SqlDao().deleteProductsCart(db, product.id)


                    db = DatabaseHelper(mContext)

                    val newProductsList = SqlDao().getProductsCart(db)
                    productsList = newProductsList
                    notifyDataSetChanged()
                    updateTotalPrice()
                }.show()
        }


        val uri = product.images[0]

        Picasso.get()
            .load(uri)
            .into(holder.cardviewProductImage)
    }


    interface ProductAdapterListener {
        fun onNumberOfProductTotalPriceChanged(numberOfProductTotalprice: Int)
    }

}