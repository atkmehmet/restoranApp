package com.example.restorant.restaurants.data.presentation.list

import com.example.restorant.restaurants.data.domain.Restaurant

data class RestaurantsScreenState (
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
        )
