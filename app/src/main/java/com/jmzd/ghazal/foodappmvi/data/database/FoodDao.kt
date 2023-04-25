package com.jmzd.ghazal.foodappmvi.data.database

import androidx.room.*
import com.jmzd.ghazal.foodappmvi.utils.FOOD_DB_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFood(entity: FoodEntity)

    @Delete
    suspend fun deleteFood(entity: FoodEntity)

    @Query("SELECT * FROM $FOOD_DB_TABLE")
    fun getAllFoods(): Flow<MutableList<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_DB_TABLE WHERE id = :id)")
    fun existsFood(id: Int): Flow<Boolean>
}