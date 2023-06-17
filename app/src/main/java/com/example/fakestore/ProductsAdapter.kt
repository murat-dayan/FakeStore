package com.example.fakestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsAdapter(val mContext:Context, val productsList: List<Products>):
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>()
{


    inner class ProductsHolder(view:View): RecyclerView.ViewHolder(view){
        var cardviewProduct: CardView
        var cardviewProductImageView: ImageView
        var cardviewProductName: TextView
        var cardviewProductPrice: TextView
        var cardviewProductFavImage: ImageView
        init {
            cardviewProduct= view.findViewById(R.id.cardviewProduct)
            cardviewProductImageView= view.findViewById(R.id.cardviewProductImageView)
            cardviewProductName= view.findViewById(R.id.cardviewProductName)
            cardviewProductPrice= view.findViewById(R.id.cardviewProductPrice)
            cardviewProductFavImage= view.findViewById(R.id.cardviewProductFavImage)
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

        holder.cardviewProduct.setOnClickListener {
            val intent= Intent(mContext,ProductDetailActivity::class.java)
            intent.putExtra("product",product)
            mContext.startActivity(intent)
        }


        holder.cardviewProductFavImage.setOnClickListener {

            val popupMenu= PopupMenu(mContext, holder.cardviewProductFavImage)
            popupMenu.inflate(R.menu.more_menu)

            popupMenu.setOnMenuItemClickListener {menuItem->


                when(menuItem.itemId){
                    R.id.action_add_fav->{

                        val db= DatabaseHelper(mContext)
                        val isLoad=SqlDao().addProduct(    db,
                                                product,
                                                product.id,
                                                product.title,
                                                product.price,
                                                product.description,
                                                product.images[0],
                                                product.creationAt,
                                                product.updatedAt,
                                                product.category.id)

                        if (isLoad){
                            Toast.makeText(mContext,"Product added",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(mContext,"Product is already exist",Toast.LENGTH_SHORT).show()
                        }

                    }
                    else-> true
                }
                true
            }
            popupMenu.show()

        }



    }


}