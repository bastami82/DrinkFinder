package data.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DrinksResultDto(
    val drinks: List<DrinkResultDto>
)

@Serializable
@SerialName("drinks")
data class DrinkResultDto(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?
)