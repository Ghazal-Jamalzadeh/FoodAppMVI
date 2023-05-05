package com.jmzd.ghazal.foodappmvi.viewmodel.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.foodappmvi.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvi.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {
    val detailIntent = Channel<DetailIntent>()
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state: StateFlow<DetailState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() = viewModelScope.launch {
        detailIntent.consumeAsFlow().collect { intent : DetailIntent ->
            when (intent) {
                is DetailIntent.FinishPage -> finishingPage()
                is DetailIntent.LoadDetail -> fetchingFoodDetail(intent.foodId)
                is DetailIntent.SaveToFavourite -> saveFavorite(intent.entity)
                is DetailIntent.RemoveFromFavourite -> deleteFavorite(intent.entity)
                is DetailIntent.CheckFavourite -> existsFavorite(intent.foodId)
            }
        }
    }

    private fun existsFavorite(id: Int) = viewModelScope.launch {
        repository.existsFood(id).collect { isFavourite : Boolean ->
            _state.emit(DetailState.FavouriteStatusLoaded(isFavourite))
        }
    }

    private fun deleteFavorite(entity: FoodEntity) = viewModelScope.launch {
        _state.emit(DetailState.RemovedFromFavourite(repository.deleteFood(entity)))
    }

    private fun saveFavorite(entity: FoodEntity) = viewModelScope.launch {
        _state.emit(DetailState.AddedToFavourite(repository.saveFood(entity)))
    }

    private fun fetchingFoodDetail(id: Int) = viewModelScope.launch {
        val response = repository.detailFood(id)
        _state.emit(DetailState.Loading)
        when (response.code()) {
            in 200..202 -> {
                _state.emit(DetailState.DetailLoaded(response.body()!!))
            }
            in 400..499 -> {
                _state.emit(DetailState.Error(""))
            }
            in 500..599 -> {
                _state.emit(DetailState.Error(""))
            }
        }
    }

    private fun finishingPage() = viewModelScope.launch {
        _state.emit(DetailState.FinishPage)
    }
}