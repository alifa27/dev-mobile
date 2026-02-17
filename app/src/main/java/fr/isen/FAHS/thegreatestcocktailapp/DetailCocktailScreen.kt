package fr.isen.FAHS.thegreatestcocktailapp // <--- VERIFIE BIEN CE NOM

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.* // Contient le getValue / setValue
import androidx.compose.runtime.getValue // AJOUTÉ
import androidx.compose.runtime.setValue // AJOUTÉ
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun DetailCocktailScreen(drinkId: String) {
    val context = LocalContext.current
    // On précise le type <DrinkModel?> pour aider le compilateur
    var drink by remember { mutableStateOf<DrinkModel?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(drinkId) {
        // Vérifie que FavoritesManager est bien dans le même package
        isFavorite = FavoritesManager.getFavorites(context).contains(drinkId)
        try {
            val response = NetworkManager.apiService.getDrinkDetails(drinkId)
            drink = response.drinks.firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    drink?.let { item ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    FavoritesManager.toggleFavorite(context, drinkId)
                    isFavorite = !isFavorite
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }

            AsyncImage(
                model = item.strDrinkThumb,
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = item.strDrink, style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Instructions:", style = MaterialTheme.typography.titleMedium)
            // strInstructions doit exister dans DrinkModel
            Text(text = item.strInstructions ?: "No instructions available.")
// ... après le titre du cocktail ...

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Ingrédients :", style = MaterialTheme.typography.titleMedium)
            item.getIngredients().forEach { ingredient ->
                Text(
                    text = "• $ingredient",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

// ... tes instructions ici ...
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

}