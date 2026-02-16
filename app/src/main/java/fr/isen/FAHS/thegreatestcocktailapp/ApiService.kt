package fr.isen.FAHS.thegreatestcocktailapp

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResult

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinkResult

    @GET("random.php")
    suspend fun getRandomDrink(): DrinkResult
}