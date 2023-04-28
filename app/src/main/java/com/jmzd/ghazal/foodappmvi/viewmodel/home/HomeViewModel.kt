package com.jmzd.ghazal.foodappmvi.viewmodel.home

import android.R
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
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel(){

    val intentChannel = Channel<HomeIntent>()
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state : StateFlow<HomeState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() = viewModelScope.launch {
        intentChannel.consumeAsFlow().collect{ intent : HomeIntent ->
            when(intent){
                is HomeIntent.LoadFilterLetters -> fetchingLetters()
                is HomeIntent.LoadRandomBanner -> fetchingBanner()
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
                _state.emit(HomeState.Error( message = ""))
            }
            in 500..599 -> {
                _state.emit(HomeState.Error(message = ""))
            }
        }
    }


}