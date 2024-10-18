package data.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class DrinksDetailDto(
    val drinks: List<DrinkDetailDto>
)

@Serializable
@SerialName("drinks")
data class DrinkDetailDto(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?,
    val strInstructions: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
) {
    fun getIngredients(): List<Pair<String, String>> {
        val ingredients = mutableListOf<Pair<String?, String?>>()
        val pair1 = Pair(strIngredient1, strMeasure1)
        val pair2 = Pair(strIngredient2, strMeasure2)
        val pair3 = Pair(strIngredient3, strMeasure3)
        val pair4 = Pair(strIngredient4, strMeasure4)
        val pair5 = Pair(strIngredient5, strMeasure5)
        val pair6 = Pair(strIngredient6, strMeasure6)
        val pair7 = Pair(strIngredient7, strMeasure7)
        val pair8 = Pair(strIngredient8, strMeasure8)
        val pair9 = Pair(strIngredient9, strMeasure9)
        val pair10 = Pair(strIngredient10, strMeasure10)
        val pair11 = Pair(strIngredient11, strMeasure11)
        val pair12 = Pair(strIngredient12, strMeasure12)
        val pair13 = Pair(strIngredient13, strMeasure13)
        val pair14 = Pair(strIngredient14, strMeasure14)
        val pair15 = Pair(strIngredient15, strMeasure15)

        ingredients.add(pair1)
        ingredients.add(pair2)
        ingredients.add(pair3)
        ingredients.add(pair4)
        ingredients.add(pair5)
        ingredients.add(pair6)
        ingredients.add(pair7)
        ingredients.add(pair8)
        ingredients.add(pair9)
        ingredients.add(pair10)
        ingredients.add(pair11)
        ingredients.add(pair12)
        ingredients.add(pair13)
        ingredients.add(pair14)
        ingredients.add(pair15)

        val filteredList = ingredients.mapNotNull { (first, second) ->
            first?.takeIf { it.isNotBlank() }?.let { notNullOrEmptyFirst ->
                second?.takeIf { it.isNotBlank() }?.let { notNullOrEmptySecond ->
                    Pair(notNullOrEmptyFirst, notNullOrEmptySecond)
                }
            }
        }
        return filteredList
    }
}

