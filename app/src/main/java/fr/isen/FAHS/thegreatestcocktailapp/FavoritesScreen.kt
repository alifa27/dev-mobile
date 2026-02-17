package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DarkPurple
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DeepOrange

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val context = LocalContext.current
    // Liste des objets cocktails complets
    var favoriteDrinks by remember { mutableStateOf<List<DrinkModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Charger les favoris au lancement de l'onglet
    LaunchedEffect(Unit) {
        val favoriteIds = FavoritesManager.getFavorites(context)
        val tempDrinks = mutableListOf<DrinkModel>()

        try {
            for (id in favoriteIds) {
                val response = NetworkManager.apiService.getDrinkDetails(id)
                response.drinks.firstOrNull()?.let { tempDrinks.add(it) }
            }
            favoriteDrinks = tempDrinks
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Mes Favoris",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                color = DarkPurple
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepOrange)
            }
        } else if (favoriteDrinks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Tu n'as pas encore de favoris 🍸", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favoriteDrinks) { drink ->
                    FavoriteCard(drink) {
                        navController.navigate("detail/${drink.idDrink}")
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteCard(drink: DrinkModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = drink.strDrinkThumb,
                contentDescription = null,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = drink.strDrink,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkPurple
                )
            )
        }
    }
}