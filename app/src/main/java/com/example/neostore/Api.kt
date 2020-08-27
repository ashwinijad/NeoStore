package com.example.neostore

import com.example.neostore.Cart.CartResponse
import com.example.neostore.Cart.DeleteResponse
import com.example.neostore.Login.LoginResponse
import com.example.neostore.Order.Order_Response_Base
import com.example.neostore.OrderDetail.Order_Detail_Response_Base
import com.example.neostore.ProductDetails.Product_base_response
import com.example.neostore.ProductDetails.Rate_Response
import com.example.neostore.ProductDetails.ResponseBaseCartAdd
import com.example.neostore.ResetPassword.Reset_Reponse_Base
import com.example.neostore.myaccount.My_account_base_response
import com.example.neostore.table.Table_response
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface Api {

    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("first_name") first_name:String,
        @Field("last_name") last_name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("confirm_password") confirm_password:String,
    @Field("gender") gender:String,
    @Field("phone_no") phone_no:String)
            :Call<DefaultResponse>

    @FormUrlEncoded
    @POST("addToCart")
    fun buynow(
        @Header("access_token") token: String,

        @Field("product_id") product_id:String,
        @Field("quantity") quantity: Int)
            :Call<ResponseBaseCartAdd>


    @GET("cart")
    fun listcart(
        @Header("access_token") token: String)
            :Call<CartResponse>

    @FormUrlEncoded
    @POST("deleteCart")
    fun deletecart(
        @Header("access_token") token: String,

        @Field("product_id") product_id: Int)
            :Call<DeleteResponse>

    @FormUrlEncoded
    @POST("editCart")
    fun editCart(
        @Header("access_token") token: String,

        @Field("product_id") product_id: Int,
        @Field("quantity") quantity: Int)
            :Call<DeleteResponse>



    @FormUrlEncoded
    @POST("forgot")
    fun emailForgotpwd(
        @Field("email") email:String)
            :Call<ForgotResponse>




    @FormUrlEncoded
    @POST("change")
    fun resetpassword(@Header("access_token") token: String,
        @Field("old_password") old_password:String,
                      @Field("password") password:String,

                      @Field("confirm_password") confirm_password: String)
            :Call<Reset_Reponse_Base>
    @FormUrlEncoded
    @POST("setRating")
    fun setRating(@Field("product_id")  product_id:String
                  , @Field("rating") value:Float)

            : Call<Rate_Response>


    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String)
            :Call<LoginResponse>



    @Multipart
    @POST("update")
    fun useredit(
        @Header("access_token") token: String,
        @PartMap() images: MutableMap<String, RequestBody>)
            :Call<LoginResponse>

    @GET("getList")
    fun getAllPhotos(@Query("product_category_id")  product_category_id:String
                     , @Query("value") value:Int)

            : Call<Table_response>

    @GET("getUserData")
    fun fetchUser(@Header("access_token") token: String)
            : Call<My_account_base_response>

    @GET("getDetail")
    fun fetchUserdetail(@Query("product_id")  product_id:String)
            : Call<Product_base_response>

    @FormUrlEncoded
    @POST("order")
    fun ordernow(
        @Header("access_token") token: String,

        @Field("address") address: String)
            :Call<ForgotResponse>


    @GET("orderList")
    fun getAllOrderList(@Header("access_token")  token:String)

            : Call<Order_Response_Base>


    @GET("orderDetail")
    fun fetchorderdetail(@Header("access_token")  token:String,
    @Query("order_id")  order_id:String)
            : Call<Order_Detail_Response_Base>


}