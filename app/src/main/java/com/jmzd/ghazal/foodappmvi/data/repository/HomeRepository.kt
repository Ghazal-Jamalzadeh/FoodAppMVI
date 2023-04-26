package com.jmzd.ghazal.foodappmvi.data.repository

import com.jmzd.ghazal.foodappmvi.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api : ApiServices) {
    suspend fun randomFood() = api.foodRandom()
    suspend fun categoriesList() = api.categoriesList()
    suspend fun foodsList(letter: String) = api.foodsList(letter)
    suspend fun searchFood(letter: String) = api.searchFood(letter)
    suspend fun foodByCategory(letter: String) = api.foodsByCategory(letter)
}