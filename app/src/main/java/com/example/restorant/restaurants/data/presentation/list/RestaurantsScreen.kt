package com.example.restorant.restaurants.data.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restorant.restaurants.data.domain.Restaurant

@Composable
fun RestaurantsScreen(state: RestaurantsScreenState,
                      onItemClick: (id: Int) -> Unit = {}
                      ,onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit
) {
  //  val viewModel: RestaurantViewModel = viewModel()
   // val restaurants = viewModel.state.value
  //  val state = viewModel.state.value
  //  val isLoading = restaurants.isEmpty()
    Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
        ) {
            items(state.restaurants) { restaurant ->
                RestaurantItem(restaurant , onFavoriteClick =
                {id, oldValue ->onFavoriteClick(id, oldValue)},
                    onItemClick={id -> onItemClick(id)})
            }
        }
        if(state.isLoading)
            CircularProgressIndicator()
        if (state.error != null)
            Text(state.error)
    }

    }


@Composable
fun RestaurantItem(item: Restaurant,
                   onFavoriteClick: (id: Int,oldValue: Boolean) -> Unit,
                   onItemClick: (id: Int) -> Unit) {
    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            RestaurantIcon(Icons.Filled.Place, Modifier.weight(0.15f)){
                    onFavoriteClick(item.id,item.isFavorite)
            }
            RestaurantDetails(item.title, item.description, Modifier.weight(0.7f))
            RestaurantIcon(icon, Modifier.weight(0.15f)) {
                onItemClick(item.id)
            }
        }
    }
}

@Composable
 fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = { }) {
    Image(
        imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}

@Composable
 fun RestaurantDetails(title: String, description: String, modifier: Modifier,
                       horizontalAlignment: Alignment.Horizontal = Alignment.Start) {
    Column(modifier = modifier,
        horizontalAlignment = horizontalAlignment) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

