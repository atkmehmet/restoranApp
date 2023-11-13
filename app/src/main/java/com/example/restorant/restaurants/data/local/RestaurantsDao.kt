package com.example.restorant.restaurants.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.restorant.restaurants.data.domain.Restaurant

@Dao
interface RestaurantsDao {
    @Query("SELECT * FROM restaurant")
    suspend fun getAll():List<LocalRestaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(restaurants:List<LocalRestaurant>)

    @Update(entity = LocalRestaurant::class)
    suspend fun update(partialRestaurant: PartialLocalRestaurant)

    @Update(entity = LocalRestaurant::class)
    suspend fun updateAll(partialRestaurants:
                          List<PartialLocalRestaurant>)
    @Query("SELECT * FROM restaurant WHERE is_favorite = 1")
            suspend fun getAllFavorited(): List<LocalRestaurant>

    @Query("SELECT * FROM restaurant WHERE r_id =:id" )
    suspend fun getDeatils( id:Int): LocalRestaurant

}