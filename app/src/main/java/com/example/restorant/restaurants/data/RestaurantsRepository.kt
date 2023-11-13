package com.example.restorant.restaurants.data

import com.example.restorant.restaurants.data.local.LocalRestaurant
import com.example.restorant.RestaurantsApplication
import com.example.restorant.restaurants.data.local.PartialLocalRestaurant
import com.example.restorant.restaurants.data.local.RestaurantsDb
import com.example.restorant.restaurants.data.remote.RestaurantsApiService
import com.example.restorant.restaurants.data.domain.Restaurant
import com.example.restorant.restaurants.data.local.RestaurantsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsRepository @Inject constructor (
    private val restInterface:RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao
        ){
    /*
    private var restInterface: RestaurantsApiService = Retrofit.Builder ()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://mydatabase-90299-default-rtdb.firebaseio.com/")
        .build()
            .create( RestaurantsApiService::class.java)

    private var restaurantsDao = RestaurantsDb.getDaoInstance(RestaurantsApplication.getAppContext())
    */
    suspend fun getRestaurant() :List<Restaurant>{
        return withContext(Dispatchers.IO){
            return@withContext restaurantsDao.getAll().map {
                Restaurant(it.id, it.title,
                    it.description, it.isFavorite)
            }
        }
    }

    suspend fun getRestaurantDetails(id:Int) :Restaurant{
        return withContext(Dispatchers.IO){
            val returnValue=restaurantsDao.getDeatils(id)
            return@withContext Restaurant(
                returnValue.id,
                returnValue.title,
                returnValue.description,
                returnValue.isFavorite
            )
        }
    }


     suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean){
         withContext(Dispatchers.IO) {
             restaurantsDao.update(
                 PartialLocalRestaurant(
                     id = id,
                     isFavorite = value
                 )
             )
         }

     }
     suspend fun loadRestaurants() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                    }
                    else -> throw Exception("Something went wrong. We have no data.")
                }
            }
        }
    }
    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface
            .getRestaurants()
        val favoriteRestaurants = restaurantsDao
            .getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                false
            )
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(it.id, true)
            })
    }
}