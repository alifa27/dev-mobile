package fr.isen.FAHS.thegreatestcocktailapp

// Garde uniquement ce qui concerne les CATEGORIES ici
data class CategoryModel(
    val strCategory: String
)

data class CategoryResponse(
    val drinks: List<CategoryModel>
)
