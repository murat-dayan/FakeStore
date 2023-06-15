package com.example.fakestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoriesAdapter(var mContext: Context, var categoryList: List<Categories>):
    RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>(){

        inner class CategoriesHolder(view:View) : RecyclerView.ViewHolder(view){

            var cardviewCategories: CardView
            var cardviewCategoriesName: TextView

            init {

                cardviewCategories= view.findViewById(R.id.cardviewCategories)
                cardviewCategoriesName= view.findViewById(R.id.cardviewCategoriesName)

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val design= LayoutInflater.from(mContext).inflate(R.layout.cardview_categories,parent,false)
        return CategoriesHolder(design)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        val category= categoryList.get(position)



        holder.cardviewCategoriesName.setText(category.name)

        holder.cardviewCategories.setOnClickListener {

            val intent= Intent(mContext,ProductsActivity::class.java)
            intent.putExtra("category",category)
            mContext.startActivity(intent)

        }
    }

}