package fr.isen.FAHS.thegreatestcocktailapp

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DeepOrange
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 1. Définition des écrans pour la BottomBar
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Catalogue : Screen("categories", "Catalogue", Icons.AutoMirrored.Filled.List)
    object Favoris : Screen("favoris", "Favoris", Icons.Default.Favorite)
    object Random : Screen("random", "Surprise", Icons.Default.Refresh)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- DÉBUT DE L'ASTUCE DU SPLASH SCREEN (2 Secondes) ---
        var isReady = false

        // On lance un chrono secret de 2 secondes
        lifecycleScope.launch {
            delay(2000)
            isReady = true
        }

        // On bloque l'affichage de l'application tant que le chrono n'est pas fini
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isReady) {
                        // Le chrono est fini, on enlève le blocage et on affiche l'appli
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // On fige l'écran sur ton logo
                        false
                    }
                }
            }
        )
        // --- FIN DE L'ASTUCE ---

        setContent {
            TheGreatestCocktailAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.Catalogue, Screen.Favoris, Screen.Random)

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepOrange,
                            selectedTextColor = DeepOrange,
                            indicatorColor = DeepOrange.copy(alpha = 0.1f)
                        ),
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Le NavHost gère le changement d'écran
        NavHost(
            navController = navController,
            startDestination = Screen.Catalogue.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Onglet 1 : Catalogue
            composable(Screen.Catalogue.route) {
                CategoriesScreen(onCategoryClick = { category ->
                    val encodedCat = android.net.Uri.encode(category)
                    navController.navigate("drinks/$encodedCat")
                })
            }

            // Onglet 2 : Favoris
            composable(Screen.Favoris.route) {
                FavoritesScreen(navController)
            }

            // Onglet 3 : Surprise (Random)
            composable(Screen.Random.route) {
                RandomDrinkScreen()
            }
//hey
            // Écrans de navigation interne (Détails et Listes)
            composable(
                "drinks/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val cat = backStackEntry.arguments?.getString("category") ?: ""
                DrinksListScreen(categoryName = cat, navController = navController)
            }

            composable(
                "detail/{drinkId}",
                arguments = listOf(navArgument("drinkId") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("drinkId") ?: ""
                DetailCocktailScreen(drinkId = id)
            }
        }
    }
}