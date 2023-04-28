package com.jmzd.ghazal.foodappmvi.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.foodappmvi.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val intentChannel = Channel<HomeIntent>()
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() = viewModelScope.launch {
        intentChannel.consumeAsFlow().collect { intent: HomeIntent ->
            when (intent) {
                is HomeIntent.LoadFilterLetters -> fetchingLetters()
                is HomeIntent.LoadRandomBanner -> fetchingBanner()
                is HomeIntent.LoadCategoriesList -> fetchingCategories()
                is HomeIntent.LoadFoodsByLetter -> fetchingFoodsByLetter(intent.letter)
                is HomeIntent.LoadFoodsBySearch -> fetchingFoodsBySearch(intent.search)
                is HomeIntent.LoadFoodsByCategory -> fetchingFoodsByCategory(intent.category)
            }

        }
    }


    private suspend fun fetchingLetters() {
        val letters = listOf('A'..'Z').flatten().toMutableList()
        _state.emit(HomeState.FilterLettersLoaded(letters = letters))
    }

    private suspend fun fetchingBanner() {
        val response = repository.randomFood()
        when (response.code()) {
            in 200..202 -> {
                _state.emit(HomeState.RandomBannerLoaded(banner = response.body()?.meals?.get(0)))
            }
            in 400..499 -> {
                _state.emit(HomeState.Error(message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }
    }


    private fun fetchingCategories() = viewModelScope.launch {
        val response = repository.categoriesList()
        _state.emit(HomeState.LoadingCategories)
        when (response.code()) {
            in 200..202 -> {
                _state.emit(HomeState.CategoriesLoaded(categories = response.body()!!.categories))
            }
            in 400..499 -> {
                _state.emit(HomeState.Error(message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }
    }

    private suspend fun fetchingFoodsByLetter(letter: String) {

        val response = repository.foodsList(letter)
        _state.emit(HomeState.LoadingFoods)
        when (response.code()) {
            in 200..202 -> {
                /*اگر به جای value امیت قرار میدادیم دیگه نمیتونستیم دستورات شرطی رو به این شکل بنویسیم*/
                /* تفاوت اصلی emit & value روی تردهایی است که اطلاعات رو میفرستن */
                _state.value = if (response.body()!!.meals != null) {
                    HomeState.FoodsListLoaded(foods = response.body()!!.meals!!)
                } else {
                    HomeState.Empty
                }
            }
            in 400..499 -> {
                _state.emit(HomeState.Error(message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }

    }

    private suspend fun fetchingFoodsBySearch(search: String) {

        val response = repository.searchFood(search)
        _state.emit(HomeState.LoadingFoods)
        when (response.code()) {
            in 200..202 -> {
                _state.value = if (response.body()!!.meals != null) {
                    HomeState.FoodsListLoaded( foods = response.body()!!.meals!!)
                } else {
                    HomeState.Empty
                }
            }
            in 400..499 -> {
                _state.emit(HomeState.Error(message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }

    }

    private suspend fun fetchingFoodsByCategory(category: String) {


        val response = repository.foodByCategory(category)
        _state.emit(HomeState.LoadingFoods)
        when (response.code()) {
            in 200..202 -> {
                _state.value = if (response.body()!!.meals != null) {
                    HomeState.FoodsListLoaded( foods = response.body()!!.meals!!)
                } else {
                    HomeState.Empty
                }
            }
            in 400..499 -> {
                _state.emit(HomeState.Error(message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }


    }


}