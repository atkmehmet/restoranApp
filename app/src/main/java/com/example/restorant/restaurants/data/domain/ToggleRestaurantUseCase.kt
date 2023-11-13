package com.example.restorant.restaurants.data.domain

import com.example.restorant.restaurants.data.RestaurantsRepository
import javax.inject.Inject

class ToggleRestaurantUseCase @Inject constructor(
    private val repository: RestaurantsRepository,
    private val getSortedRestaurantsUseCase: GetSortedRestaurantsUseCase,

) {

   // private  val repository: RestaurantsRepository = RestaurantsRepository()
   // private val getSortedRestaurantsUseCase= GetSortedRestaurantsUseCase()
    suspend  operator fun  invoke(id:Int,oldValue:Boolean):List<Restaurant>{
        val newFav= oldValue.not()
        repository.toggleFavoriteRestaurant(id, newFav)
        return getSortedRestaurantsUseCase.invoke()
    }
}