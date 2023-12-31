package com.example.restorant.restaurants.data.domain

import com.example.restorant.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase:GetSortedRestaurantsUseCase) {


    suspend operator fun invoke():List<Restaurant>{

        repository.loadRestaurants()
        return getSortedRestaurantsUseCase.invoke()
    }
}