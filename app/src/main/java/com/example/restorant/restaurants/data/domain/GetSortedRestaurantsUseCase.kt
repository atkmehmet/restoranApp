package com.example.restorant.restaurants.data.domain

import com.example.restorant.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {

   // private val repository: RestaurantsRepository =  RestaurantsRepository()

    suspend  operator fun invoke  ():List<Restaurant>{
        return repository.getRestaurant()
            .sortedBy { it.title}
    }
}