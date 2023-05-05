package com.jmzd.ghazal.foodappmvi.viewmodel.detail

import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList

sealed class DetailState{
    object FinishPage : DetailState()
    object Loading : DetailState()
    data class Error (val message : String) : DetailState()
    data class DetailLoaded(val detail : ResponseFoodsList) : DetailState()
    data class FavouriteStatusLoaded (val isFavourite : Boolean) : DetailState()
    data class AddedToFavourite (val unit : Unit) : DetailState()
    data class RemovedFromFavourite (val unit : Unit ) : DetailState()
}
