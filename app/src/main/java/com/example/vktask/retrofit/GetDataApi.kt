package com.example.vktask.retrofit


import com.example.vktask.data.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataApi {
    @GET("/products")
    suspend fun getProducts(@Query("skip") skip:Int,@Query("limit") limit:Int): Response<Products>

    @GET("/products/search")
    suspend fun getProductsSearch(@Query("q") search:String): Response<Products>

//    @GET("/api/materials")
//    suspend fun getMaterials(@Header("Authorization") token:String,@Query("search") search:String): Response<Materials>
//
//    @GET("/api/materials/{id}")
//    suspend fun getMaterialDetails(@Header("Authorization") token:String, @Path("id") id:Int): Response<MaterialDetails>
}

