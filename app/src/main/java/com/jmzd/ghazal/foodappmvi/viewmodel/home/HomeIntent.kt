package com.jmzd.ghazal.foodappmvi.viewmodel.home

sealed class HomeIntent {
    object LoadFilterLetters : HomeIntent()
    object LoadRandomBanner : HomeIntent()

}
