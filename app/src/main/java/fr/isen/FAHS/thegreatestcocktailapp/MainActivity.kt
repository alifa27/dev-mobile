package fr.isen.FAHS.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheGreatestCocktailAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "categories") {
                        // Écran des catégories (récupère les données de l'API)
                        composable("categories") {
                            CategoriesScreen(onCategoryClick = { category ->
                                navController.navigate("drinks/$category")
                            })
                        }

                        // Écran de la liste des boissons par catégorie
                        composable(
                            "drinks/{category}",
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("category") ?: ""
                            // On appelle l'écran des boissons (Code à créer juste après)
                            DrinksListScreen(categoryName)
                        }
                    }
                }
            }
        }
    }
}