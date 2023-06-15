package com.example.fakestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductsAdapter(val mContext:Context, val productsList: List<Products>):
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>()
{

    inner class ProductsHolder(view:View): RecyclerView.ViewHolder(view){
        var cardviewProduct: CardView
        var cardviewProductImageView: ImageView
        var cardviewProductName: TextView
        var cardviewProductPrice: TextView

        init {
            cardviewProduct= view.findViewById(R.id.cardviewProduct)
            cardviewProductImageView= view.findViewById(R.id.cardviewProductImageView)
            cardviewProductName= view.findViewById(R.id.cardviewProductName)
            cardviewProductPrice= view.findViewById(R.id.cardviewProductPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val design= LayoutInflater.from(mContext).inflate(R.layout.cardview_products,parent,false)
        return  ProductsHolder(design)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        val product= productsList.get(position)

        holder.cardviewProductName.setText(product.title)
        holder.cardviewProductPrice.setText("${(product.price)}€")

        val url= product.images[0]

        Picasso.get()
            .load(url)
            .into(holder.cardviewProductImageView)



    }
}