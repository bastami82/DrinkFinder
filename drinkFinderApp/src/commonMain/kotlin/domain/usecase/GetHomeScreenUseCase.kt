package domain.usecase

import networking.DrinksService

class GetHomeScreenUseCase(
    private val drinksService: DrinksService,
) {
    suspend fun getLatestDrinks() = drinksService.getLatestDrinks()
    suspend fun getPopularDrinks() = drinksService.getPopularDrinks()
    suspend fun getNonAlcoholicDrinks() = drinksService.getNonAlcoholicDrinks()
    suspend fun getCocktailGlassDrinks() = drinksService.getCocktailGlassDrinks()
    suspend fun getChampagneFluteDrinks() = drinksService.getChampagneFluteDrinks()
}