package com.example.neostore

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null


    private const val BASE_URL = "http://staging.php-dev.in:8844/trainingapp/api/users/"

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val retrofit1: Retrofit = builder.build()

    private const val BASE_URL_TABLE = "http://staging.php-dev.in:8844/trainingapp/api/products/"
private const val BASE_URL_CART="http://staging.php-dev.in:8844/trainingapp/api/"
    private const val BASE_URL_ORDER="http://staging.php-dev.in:8844/trainingapp/api/"
     val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
    val instancetable: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_TABLE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
    val instancecart: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_CART)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
    val instanceorder: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_ORDER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
   /* val retrofitInstance: Retrofit by Lazy {
        get() {
            if (RetrofitClientInstance.retrofit == null) {
                RetrofitClientInstance.retrofit = Retrofit.Builder()
                    .baseUrl(RetrofitClientInstance.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofit?.create(Api::class.java)

            }
            return RetrofitClientInstance.retrofit
        }

    }*/
}

