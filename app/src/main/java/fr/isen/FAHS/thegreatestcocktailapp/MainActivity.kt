package fr.isen.FAHS.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // LE CODE DE NAVIGATION VA ICI
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "categories") {
                        composable("categories") {
                            CategoriesScreen(onCategoryClick = { category ->
                                navController.navigate("drinks/$category")
                            })
                        }
                        composable("drinks/{category}") { backStackEntry ->
                            val categoryName = backStackEntry.arguments?.getString("category") ?: ""
                            Text(text = "Liste des boissons pour : $categoryName")
                        }
                        composable("detail") {
                            DetailCocktailScreen()
                        }
                    }
                }
            }
        }

    }
}