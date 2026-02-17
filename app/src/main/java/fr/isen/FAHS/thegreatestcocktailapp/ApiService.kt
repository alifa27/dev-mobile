package fr.isen.FAHS.thegreatestcocktailapp // Garde ton package actuel pour l'instant

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse // Changé CategoryResult -> CategoryResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinkResponse // Changé DrinkResult -> DrinkResponse

    @GET("random.php")
    suspend fun getRandomDrink(): DrinkResponse // Changé DrinkResult -> DrinkResponse

    @GET("lookup.php")
    suspend fun getDrinkDetails(@Query("i") id: String): DrinkResponse
}