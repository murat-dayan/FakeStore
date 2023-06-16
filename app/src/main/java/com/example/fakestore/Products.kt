package com.example.fakestore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class Products(

    @SerializedName("id")
    @Expose
    var id:Int,
    @SerializedName("title")
    @Expose
    var title:String,
    @SerializedName("price")
    @Expose
    var price:Int,
    @SerializedName("description")
    @Expose
    var description:String,
    @SerializedName("images")
    @Expose
    var images: List<String> = emptyList(),
    @SerializedName("creationAt")
    @Expose
    var creationAt:String,
    @SerializedName("updatedAt")
    @Expose
    var updatedAt:String,
    @SerializedName("category")
    @Expose
    var category: Categories

) : java.io.Serializable{}
