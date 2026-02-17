package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DarkPurple
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DeepOrange

@Composable
fun DetailCocktailScreen(drinkId: String) {
    val context = LocalContext.current
    var drink by remember { mutableStateOf<DrinkModel?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(drinkId) {
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
                .background(Color(0xFFF8F9FA)) // Fond gris pro
                .verticalScroll(rememberScrollState())
        ) {
            // --- HEADER AVEC IMAGE FULL WIDTH ---
            Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                AsyncImage(
                    model = item.strDrinkThumb,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Petit dégradé noir en haut pour bien voir l'icône de favoris
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent)))
                )

                IconButton(
                    onClick = {
                        FavoritesManager.toggleFavorite(context, drinkId)
                        isFavorite = !isFavorite
                    },
                    modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Red else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // --- CONTENU ---
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = item.strDrink,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = DarkPurple
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // SECTION INGRÉDIENTS DANS UNE CARTE
                SectionHeader(title = "Ingredients")
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        item.getIngredients().forEach { ingredient ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 6.dp)
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = DeepOrange,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = ingredient,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // SECTION INSTRUCTIONS DANS UNE CARTE
                SectionHeader(title = "Instructions")
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.strInstructions ?: "No instructions available.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 24.sp,
                            color = Color.DarkGray
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = DeepOrange)
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelLarge.copy(
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        ),
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 10.dp, start = 4.dp)
    )
}