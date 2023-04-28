package com.jmzd.ghazal.foodappmvi.viewmodel.home

sealed class HomeIntent {
    object LoadFilterLetters : HomeIntent()
    object LoadRandomBanner : HomeIntent()
    object LoadCategoriesList : HomeIntent()
    data class LoadFoodsByLetter(val letter : String) : HomeIntent()
    data class LoadFoodsBySearch(val search : String) : HomeIntent()
    data class LoadFoodsByCategory (val category : String) : HomeIntent()

}
