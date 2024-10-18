package domain.detail

data class DrinkDetailDomainModel(
    val id: String,
    val name: String,
    val iconPath: String,
    val ingredients: List<Pair<String, String>>,
    val instructions: String,
)