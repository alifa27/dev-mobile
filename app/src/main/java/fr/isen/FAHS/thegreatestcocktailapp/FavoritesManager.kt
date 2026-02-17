package fr.isen.FAHS.thegreatestcocktailapp

import android.content.Context

object FavoritesManager {
    private const val PREFS_NAME = "cocktail_prefs"
    private const val FAVORITES_KEY = "favorites_list"

    fun toggleFavorite(context: Context, drinkId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favorites = getFavorites(context).toMutableSet()
        if (favorites.contains(drinkId)) favorites.remove(drinkId) else favorites.add(drinkId)
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply()
    }

    fun getFavorites(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }
}