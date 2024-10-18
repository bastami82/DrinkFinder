package domain.result

data class DrinksResultDomainModel(
    val drinks: List<DrinkItemDomainModel>,
    val title: String
)

data class DrinkItemDomainModel(
    val id: String,
    val name: String,
    val iconPath: String,
)

