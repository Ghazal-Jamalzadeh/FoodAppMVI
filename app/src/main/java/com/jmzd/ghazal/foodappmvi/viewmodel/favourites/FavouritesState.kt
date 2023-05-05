package com.jmzd.ghazal.foodappmvi.viewmodel.favourites

import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity

sealed class FavouritesState {
    data class FavouritesLoaded(val favourites : MutableList<FoodEntity>) : FavouritesState()
    object Empty : FavouritesState()
}
