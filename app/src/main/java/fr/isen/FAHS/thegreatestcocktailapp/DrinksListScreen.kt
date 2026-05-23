package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun DrinksListScreen(categoryName: String, navController: NavHostController) {
    var drinks by remember { mutableStateOf<List<DrinkModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // 🚨 CORRECTION ICI : Décodage du nom de la catégorie
    val decodedCategoryName = remember(categoryName) {
        android.net.Uri.decode(categoryName)
    }

    LaunchedEffect(decodedCategoryName) {
        try {
            val response = NetworkManager.apiService.getDrinksByCategory(decodedCategoryName)
            // 🚨 CORRECTION ICI : Si response.drinks est null, on renvoie une liste vide au lieu de crasher
            drinks = response.drinks ?: emptyList()
        } catch (e: Exception) {
            drinks = emptyList() // En cas d'erreur réseau
        } finally {
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = decodedCategoryName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (drinks.isEmpty()) {
            // 🚨 CORRECTION UI : Message si la catégorie ne contient aucun cocktail
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Aucun cocktail trouvé dans cette catégorie.", color = Color.Gray)
            }
        } else {
            LazyColumn {
                items(drinks) { drink ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("detail/${drink.idDrink}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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