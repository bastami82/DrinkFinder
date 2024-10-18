package domain.usecase

import domain.detail.DetailRepository

class GetDrinkFromIdUseCase(
    private val detailRepository: DetailRepository,
) {
    suspend operator fun invoke(drinkId: String) = detailRepository.getDrink(drinkId)
}

