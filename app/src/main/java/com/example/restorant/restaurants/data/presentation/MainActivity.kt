package com.example.restorant.restaurants.data.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.restorant.restaurants.data.presentation.details.RestaurantDetailsScreen
import com.example.restorant.restaurants.data.presentation.details.RestaurantDetailsViewModel
import com.example.restorant.restaurants.data.presentation.list.RestaurantViewModel
import com.example.restorant.restaurants.data.presentation.list.RestaurantsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           //RestaurantsScreen()
            //RestaurantDetailsScreen()
            RestaurantApp()


        }
    }
}
@SuppressLint("SuspiciousIndentation")
@Composable
fun RestaurantApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "restaurants") {

        composable(route = "restaurants") {
            val viewModel: RestaurantViewModel = hiltViewModel()
            RestaurantsScreen(
                state = viewModel.state.value,
                onItemClick = { id ->
                    navController
                        .navigate("restaurants/$id")
                },
                onFavoriteClick = { id, oldValue ->
                    viewModel.toggleFavorite(id, oldValue)
                })
        }
            composable(
                route = "restaurants/{restaurant_id}",
                arguments =
                listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                })
            ) { navStackEntry ->
                val id = navStackEntry.arguments?.getInt("restaurant_id")
                val viewModel:RestaurantDetailsViewModel= hiltViewModel()

                viewModel.state.value?.let { RestaurantDetailsScreen(item = it) }
            }
        }

    }


