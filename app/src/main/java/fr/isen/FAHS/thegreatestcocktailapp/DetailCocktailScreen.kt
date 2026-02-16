package fr.isen.FAHS.thegreatestcocktailapp

import fr.isen.FAHS.thegreatestcocktailapp.R
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Ajouté pour le scroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll // Ajouté pour le scroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Version optimisée de ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Ajouté pour 'Color.Red'
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Initialisation de l'état du scroll

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails") },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Ajouté aux favoris !", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoris",
                            tint = Color.Red
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /* Action de retour */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState) // Utilisation du scrollState
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                shadowElevation = 8.dp,
                modifier = Modifier.size(200.dp)
            ) {
                Image(
                    // Remplace cocktail_image par ic_launcher_foreground (qui existe par défaut)
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Image du cocktail",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Yoghurt Cooler", style = MaterialTheme.typography.headlineMedium)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                SuggestionChip(onClick = {}, label = { Text("Other / Unknown") })
                SuggestionChip(onClick = {}, label = { Text("Non alcoholic") })
            }

            Text("🍸 Highball Glass", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📋 Ingrédients", style = MaterialTheme.typography.titleMedium)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("• Yoghurt : 1 cup")
                    Text("• Fruit : 1 cup")
                }
            }
        }
    }
}