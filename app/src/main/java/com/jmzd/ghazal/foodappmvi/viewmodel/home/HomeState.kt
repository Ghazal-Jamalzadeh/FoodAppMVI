package com.jmzd.ghazal.foodappmvi.viewmodel.home

import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList

sealed class HomeState{
    object Idle : HomeState()
    data class FilterLettersLoaded (val letters : MutableList<Char>) : HomeState()
    data class RandomBannerLoaded (val banner : ResponseFoodsList.Meal?) : HomeState()
    data class Error (val message : String) : HomeState()

}
