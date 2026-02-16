package fr.isen.FAHS.thegreatestcocktailapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesScreen(onCategoryClick: (String) -> Unit) {
    val categories = listOf("Beer", "Cocktail", "Cocoa", "Coffee", "Shot", "Soft drink")

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Catégories", style = MaterialTheme.typography.headlineLarge)
        }
        items(categories) { category ->
            Button(
                onClick = { onCategoryClick(category) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(text = category)
            }
        }
    }
}