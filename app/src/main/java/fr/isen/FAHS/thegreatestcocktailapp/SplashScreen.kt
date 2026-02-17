package fr.isen.FAHS.thegreatestcocktailapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.FAHS.thegreatestcocktailapp.ui.theme.DarkPurple
import kotlinx.coroutines.delay

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                delay(2000)

                // On récupère le contexte de manière sécurisée
                val currentContext = this@SplashScreen

                // Syntaxe alternative si le ::class.java bloque
                val intent = Intent(currentContext, MainActivity::class.java)
                currentContext.startActivity(intent)
                currentContext.finish()
            }

            Box(
                modifier = Modifier.fillMaxSize().background(DarkPurple),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cocktail_image),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}