package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DeepOrange

@Composable
fun RandomDrinkScreen() {
    var drinkId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // On appelle l'API dès que l'écran s'affiche
    LaunchedEffect(Unit) {
        try {
            val response = NetworkManager.apiService.getRandomDrink()
            // On récupère l'ID du cocktail tiré au sort
            drinkId = response.drinks.firstOrNull()?.idDrink
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = DeepOrange)
        }
    } else {
        // Si on a l'ID, on affiche directement l'écran de détail !
        drinkId?.let { id ->
            DetailCocktailScreen(drinkId = id)
        }
    }
}