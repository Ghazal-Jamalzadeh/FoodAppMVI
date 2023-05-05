package com.jmzd.ghazal.foodappmvi.data.repository

import com.jmzd.ghazal.foodappmvi.data.database.FoodDao
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val dao: FoodDao) {
    fun foodsList() = dao.getAllFoods()
}