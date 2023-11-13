package com.example.restorant.restaurants.data.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restorant.restaurants.data.RestaurantsRepository
import com.example.restorant.restaurants.data.remote.RestaurantsApiService
import com.example.restorant.restaurants.data.domain.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor (
    private val stateHandle:SavedStateHandle,
    private  val restInterface:RestaurantsApiService,
    private val repository: RestaurantsRepository
    ) :ViewModel () {
  //  private var restInterface: RestaurantsApiService
    val state = mutableStateOf<Restaurant?>(null)

            init {
                /*
                val retrofit : Retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://mydatabase-90299-default-rtdb.firebaseio.com/")
                    .build()


                restInterface = retrofit.create(RestaurantsApiService::class.java)

*/
                    val id =stateHandle.get<Int>("restaurant_id")?:0
                viewModelScope.launch (Dispatchers.IO){
                  //  val restaurant = getRemoteRestaurant(id)
                    val restaurant=repository.getRestaurantDetails(id)
                    state.value = restaurant
                }


            }

  private suspend fun getDetails(id:Int):Restaurant{
      return withContext(Dispatchers.IO) {
          return@withContext repository.getRestaurantDetails(id)
      }
  }

    private suspend fun getRemoteRestaurant(id:Int): Restaurant {
        return withContext(Dispatchers.IO){
            val responseMap = restInterface.getRestaurant(id)
            return@withContext responseMap.values.first().let {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }
}