package com.example.restorant.restaurants.data.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restorant.restaurants.data.domain.GetInitialRestaurantsUseCase
import com.example.restorant.restaurants.data.domain.ToggleRestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor (
    private val getRestaurantsUseCase:GetInitialRestaurantsUseCase
,private val toggleRestaurantUseCase:ToggleRestaurantUseCase,
                                        private val stateHandle: SavedStateHandle) : ViewModel ()
{





    private val errorHandler= CoroutineExceptionHandler{
        _,exception->exception.printStackTrace()
        _state.value=_state.value.copy(
            error = exception.message,
            isLoading = false
        )
    }

  private  val _state = mutableStateOf(
        RestaurantsScreenState(
            restaurants = listOf(),
            isLoading = true)
    )
    val state: MutableState<RestaurantsScreenState>
        get() = _state




    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants = toggleRestaurantUseCase(id, oldValue)
            _state.value=_state.value.copy(
                restaurants = updatedRestaurants,
                isLoading = false
            )
        }
    }


    init {
            getRestaurants()
    }

 private   fun getRestaurants() {
         viewModelScope.launch (errorHandler) {
             val restaurants = getRestaurantsUseCase ()
             _state.value  = _state.value.copy(
                 restaurants = restaurants,
                 isLoading = false
             )
         }
    }
    override fun onCleared() {
        super.onCleared()
    }




}