package com.terminal.weatherapp.presentation.favorite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.terminal.weatherapp.domain.entity.City
import com.terminal.weatherapp.presentation.extentions.tempToFormattedString
import com.terminal.weatherapp.presentation.ui.theme.CardGradient
import com.terminal.weatherapp.presentation.ui.theme.Gradient
import com.terminal.weatherapp.presentation.ui.theme.Orange
import java.nio.file.WatchEvent


@Composable
fun FavoriteContent(component: FavoriteComponent) {

    val state by component.model.collectAsState()


    Scaffold {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),

            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                span = { GridItemSpan(2) }
            ) {
                SearchCard(
                    onClick = {
                        component.onClickSearch()
                    }
                )
            }

            itemsIndexed(
                items = state.cityItems,
                key = { _, item -> item.city.id }

            ) { index, item ->
                CityCard(
                    item, index,
                    onClick = {
                        component.onCityItemClick(item.city)
                    }
                )
            }

            item {
                AddFavoriteCityCard(
                    onClick = {
                        component.onClickAddFavorite()
                    }
                )
            }

        }
    }


}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCard(
    cityItem: FavoriteStore.State.CityItem, index: Int,
    onClick: () -> Unit
) {

    val gradient = getGradientByIndex(index)
    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                spotColor = gradient.shadowColor,
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {

        Box(
            modifier = Modifier
                .background(gradient.primaryGradient)
                .drawBehind {
                    drawCircle(
                        gradient.secondaryGradient,
                        center = Offset(
                            x = center.x - size.width / 10,
                            y = center.y + size.height / 2
                        ),
                        radius = size.maxDimension / 2

                    )
                }
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .clickable {
                    onClick()
                }
                .padding(24.dp)
        ) {
            when (val weatherState = cityItem.weatherState) {
                FavoriteStore.State.WeatherState.Error, FavoriteStore.State.WeatherState.Initial -> {}
                is FavoriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier.align(Alignment.TopEnd),
                        model = weatherState.iconUrl, contentDescription = null
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = weatherState.tempC.tempToFormattedString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 40.sp)
                    )
                }

                FavoriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                modifier = Modifier.align(alignment = Alignment.BottomStart),
                text = cityItem.city.name,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }

}


@Composable
private fun AddFavoriteCityCard(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier
                .sizeIn(minHeight = 196.dp)
                .fillMaxSize()
                .clickable {
                    onClick()
                }
                .padding(24.dp)
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(48.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Orange
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Add favorite",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

                )

        }


    }
}

@Composable
private fun SearchCard(
    onClick: () -> Unit,
) {
    val gradient = CardGradient.gradients[3]

    Card(
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .fillMaxSize()
                .background(gradient.primaryGradient)

        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            Text(
                text = "Search", color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(16.dp)
            )

        }
    }

}


private fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradient.gradients
    return gradients[index % gradients.size]

}
















