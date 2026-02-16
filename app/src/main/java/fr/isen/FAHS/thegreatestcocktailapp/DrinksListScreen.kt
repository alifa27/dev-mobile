package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage // Coil pour charger les photos depuis le web

@Composable
fun DrinksListScreen(categoryName: String) {
    // État pour stocker la liste des boissons de la catégorie
    var drinks by remember { mutableStateOf<List<DrinkModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // On lance l'appel API dès que l'écran s'affiche pour cette catégorie
    LaunchedEffect(categoryName) {
        try {
            val response = NetworkManager.apiService.getDrinksByCategory(categoryName)
            drinks = response.drinks
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(drinks) { drink ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Affichage de l'image du cocktail
                            AsyncImage(
                                model = drink.strDrinkThumb,
                                contentDescription = drink.strDrink,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = drink.strDrink,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}