package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DeepOrange

@Composable
fun RandomDrinkScreen() {
    var drinkId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 1. Le déclencheur : à chaque fois que ce chiffre change, on relance l'API
    var refreshTrigger by remember { mutableStateOf(0) }

    // 2. Le LaunchedEffect écoute maintenant 'refreshTrigger' au lieu de 'Unit'
    LaunchedEffect(refreshTrigger) {
        isLoading = true // On remet le chargement pendant qu'on cherche
        try {
            val response = NetworkManager.apiService.getRandomDrink()
            drinkId = response.drinks?.firstOrNull()?.idDrink
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    // 3. On utilise une Box pour pouvoir superposer le bouton par-dessus l'écran
    Box(modifier = Modifier.fillMaxSize()) {

        // --- AFFICHAGE DU COCKTAIL ---
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DeepOrange)
            }
        } else {
            drinkId?.let { id ->
                DetailCocktailScreen(drinkId = id)
            } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Impossible de trouver un cocktail. Réessaie !", color = Color.Gray)
            }
        }

        // --- BOUTON POUR RELANCER ---
        ExtendedFloatingActionButton(
            onClick = { refreshTrigger++ }, // C'est ici qu'on augmente le déclencheur !
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp), // On le décolle un peu du bas de l'écran
            containerColor = DeepOrange,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Un autre !")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Un autre !", fontWeight = FontWeight.Bold)
        }
    }
}