package com.jmzd.ghazal.foodappmvi.data.repository

import androidx.room.Dao
import com.jmzd.ghazal.foodappmvi.data.database.FoodDao
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvi.data.server.ApiServices
import com.jmzd.ghazal.foodappmvi.utils.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices , private val dao: FoodDao) {

}