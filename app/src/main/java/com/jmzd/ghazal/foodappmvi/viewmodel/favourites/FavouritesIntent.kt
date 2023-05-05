package com.jmzd.ghazal.foodappmvi.viewmodel.favourites

sealed class FavouritesIntent {
    object LoadFavourites : FavouritesIntent()
}
