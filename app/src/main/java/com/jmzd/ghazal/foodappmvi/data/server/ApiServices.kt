package com.jmzd.ghazal.foodappmvi.data.server

import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("random.php")
    suspend fun foodRandom(): Response<ResponseFoodsList>

    @GET("categories.php")
    suspend fun categoriesList() : Response<ResponseCategoriesList>

    //search.php?f=a
    @GET("search.php")
    suspend fun foodsList(@Query("f") letter : String ) : Response<ResponseFoodsList>

    //search.php?s=Arrabiata
    @GET("search.php")
    suspend fun searchFood(@Query("s") search : String ) : Response<ResponseFoodsList>

    //filter.php?c=Seafood
    @GET("filter.php")
    suspend fun foodsByCategory(@Query("c") category : String ) : Response<ResponseFoodsList>

    //lookup.php?i=52772
    @GET("lookup.php")
    suspend fun foodDetail(@Query("i") foodId : Int ) : Response<ResponseFoodsList>



}