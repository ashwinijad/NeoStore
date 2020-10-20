package com.example.neostore.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName="favoritelist")

data class Tabledata (
    @PrimaryKey
    @SerializedName("id") var id : Int,

    @SerializedName("product_category_id") val product_category_id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("producer") val producer : String,
    @SerializedName("description") val description : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("rating") val rating : String,
    @SerializedName("view_count") val view_count : Int,
    @SerializedName("created") val created : String,
    @SerializedName("modified") val modified : String,
    @SerializedName("product_images") var product_images : String
)