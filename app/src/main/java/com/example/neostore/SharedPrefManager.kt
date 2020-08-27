package com.example.neostore

import android.content.Context
import com.example.neostore.Cart.DataCart
import com.example.neostore.Cart.ProductCart

class SharedPrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val user: Data
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return Data(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getInt("role_id",0),
                sharedPreferences.getString("first_name", null),
                sharedPreferences.getString("last_name", null),
                        sharedPreferences.getString("email", null),

            sharedPreferences.getString("username", null),

            sharedPreferences.getString("profile_pic", null),

            sharedPreferences.getString("country_id", null),

            sharedPreferences.getString("gender", null),

                sharedPreferences.getString("phone_no", null).toString(),
                sharedPreferences.getString("dob", null).toString(),
                sharedPreferences.getBoolean("is_active",false),
                sharedPreferences.getString("modified", null),
                sharedPreferences.getString("created", null),

                sharedPreferences.getString("access_token", null)




                )
        }


    fun saveUser(user: Data) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", user.id)
        editor.putInt("role_id", user.role_id)
        editor.putString("first_name", user.first_name)
        editor.putString("last_name", user.last_name)
        editor.putString("email", user.email)
        editor.putString("username", user.username)
        editor.putString("profile_pic", user.profile_pic)
        editor.putString("country_id", user.country_id)
        editor.putString("gender", user.gender)
        editor.putString("phone_no", user.phone_no)
        editor.putString("dob", user.dob)
        editor.putBoolean("is_active", user.is_active)
        editor.putString("modified", user.modified)
        editor.putString("created", user.created)
        editor.putString("access_token", user.access_token)


        editor.apply()

    }
    val productcart: ProductCart
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return ProductCart(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null)!!,
                sharedPreferences.getInt("cost", -1),
                sharedPreferences.getString("product_category", null)!!,

                sharedPreferences.getString("product_images", null)!!,


                sharedPreferences.getInt("sub_total", -1)





                )
        }
/*
'    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("cost") val cost : Int,
    @SerializedName("product_category") val product_category : String,
    @SerializedName("product_images") val product_images : String,
    @SerializedName("sub_total") val sub_total : Int
 */

    fun saveproductcart(productcart: ProductCart) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", productcart.id)
        editor.putString("name", productcart.name)
        editor.putInt("cost", productcart.cost)
        editor.putString("product_category", productcart.product_category)
        editor.putString("product_images", productcart.product_images)

        editor.putInt("sub_total", productcart.sub_total)


        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}