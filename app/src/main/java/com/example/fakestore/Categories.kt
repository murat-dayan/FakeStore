package com.example.fakestore

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class Categories(



    @SerializedName("id")
    @Expose
    var id:Int,
    @SerializedName("name")
    @Expose
    var name:String,
    @SerializedName("image")
    @Expose
    var image:String,
    @SerializedName("creationAt")
    @Expose
    var creationAt:String,
    @SerializedName("updatedAt")
    @Expose
    var updatedAt:String

) : java.io.Serializable {
}