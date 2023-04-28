package com.jmzd.ghazal.foodappmvi.viewmodel.home

import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseCategoriesList.*
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvi.data.model.home.ResponseFoodsList.*

sealed class HomeState{
    object Idle : HomeState()
    object LoadingCategories : HomeState()
    object LoadingFoods : HomeState()
    data class FilterLettersLoaded (val letters : MutableList<Char>) : HomeState()
    data class RandomBannerLoaded (val banner : Meal?) : HomeState()
    data class CategoriesLoaded (val categories : MutableList<Category>) : HomeState()
    data class FoodsListLoaded (val foods : MutableList<Meal> ) : HomeState()
    object Empty : HomeState()
    data class Error (val message : String) : HomeState()


}
