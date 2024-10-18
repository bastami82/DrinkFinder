package domain.usecase

import networking.DrinksService

class GetDrinksFromSearchUseCase(
    private val drinksService: DrinksService,
) {
    suspend operator fun invoke(searchTerm: String) = drinksService.getDrinksFromSearch(searchTerm)

}