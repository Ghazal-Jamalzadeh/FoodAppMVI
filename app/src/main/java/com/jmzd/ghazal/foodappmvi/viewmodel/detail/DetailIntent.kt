package com.jmzd.ghazal.foodappmvi.viewmodel.detail

import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity

sealed class DetailIntent{
    object FinishPage : DetailIntent()
    data class LoadDetail(val foodId : Int ) : DetailIntent()
    data class CheckFavourite (val foodId : Int ) : DetailIntent()
    data class SaveToFavourite (val entity: FoodEntity) : DetailIntent()
    data class RemoveFromFavourite (val entity: FoodEntity) : DetailIntent()
}
