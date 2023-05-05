package com.jmzd.ghazal.foodappmvi.viewmodel.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvi.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavoritesRepository) : ViewModel() {
    val favoriteIntent = Channel<FavouritesIntent>()
    private val _state = MutableStateFlow<FavouritesState>(FavouritesState.Empty)
    val state: StateFlow<FavouritesState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() = viewModelScope.launch {
        favoriteIntent.consumeAsFlow().collect { intent : FavouritesIntent->
            when (intent) {
                is FavouritesIntent.LoadFavourites -> fetchingLoadFavorites()
            }
        }
    }

    private fun fetchingLoadFavorites() = viewModelScope.launch {
        repository.foodsList().collect { favourites  : MutableList<FoodEntity>->
            _state.value =
                if (favourites.isEmpty()) {
                FavouritesState.Empty
            } else {
                FavouritesState.FavouritesLoaded(favourites = favourites)
            }
        }
    }
}