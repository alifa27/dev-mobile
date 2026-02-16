package fr.isen.FAHS.thegreatestcocktailapp

data class CategoryResult(val drinks: List<CategoryModel>)
data class CategoryModel(val strCategory: String)

data class DrinkResult(val drinks: List<DrinkModel>)
data class DrinkModel(
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String
)