package fr.isen.FAHS.thegreatestcocktailapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DrinkModel(
    @SerializedName("idDrink") val idDrink: String,
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("strInstructions") val strInstructions: String? = null,
    // On ajoute les ingrédients (l'API en propose jusqu'à 15)
    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null
) : Serializable {
    // Cette fonction crée une liste propre sans les valeurs nulles ou vides
    fun getIngredients(): List<String> {
        return listOfNotNull(
            strIngredient1, strIngredient2, strIngredient3, strIngredient4,
            strIngredient5, strIngredient6, strIngredient7, strIngredient8
        ).filter { it.isNotBlank() }
    }
}

data class DrinkResponse(val drinks: List<DrinkModel>)