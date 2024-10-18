package domain.usecase

import networking.DrinksService

class GetNonAlchoholicDrinksUseCase(
    private val drinksService: DrinksService,
) {
    suspend operator fun invoke() = drinksService.getNonAlcoholicDrinks()
}