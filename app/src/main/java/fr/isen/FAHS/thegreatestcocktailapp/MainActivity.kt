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

                        // 1. Écran des Catégories (L'accueil)
                        composable("categories") {
                            CategoriesScreen(onCategoryClick = { category ->
                                navController.navigate("drinks/$category")
                            })
                        }

                        // 2. Écran de la Liste des Boissons (Appelé une seule fois)
                        composable(
                            "drinks/{category}",
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("category") ?: ""
                            // On passe les deux arguments attendus par ta fonction
                            DrinksListScreen(categoryName = categoryName, navController = navController)
                        }

                        // 3. Écran de Détail d'un Cocktail
                        composable(
                            "detail/{drinkId}",
                            arguments = listOf(navArgument("drinkId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val drinkId = backStackEntry.arguments?.getString("drinkId") ?: ""
                            DetailCocktailScreen(drinkId = drinkId)
                        }
                    }
                }
            }
        }
    }
}