package data.detail.repository

import data.detail.DrinksDetailDto
import domain.detail.DetailRepository
import networking.DrinksService
import networking.util.AppNetworkResult
import networking.util.NetworkError
import networking.util.onSuccess


class DetailRepositoryImp(private val service: DrinksService) : DetailRepository {
    override suspend fun getDrink(id: String): AppNetworkResult<DrinksDetailDto, NetworkError> {
        return service.getDrinkFromId(id)
            .onSuccess {
                it.drinks.filter { dto ->
                    dto.idDrink.isNullOrEmpty() ||
                            dto.strDrink.isNullOrEmpty() ||
                            dto.strDrinkThumb.isNullOrEmpty() ||
                            dto.strInstructions.isNullOrEmpty() // || dto.strYoutube.isNullOrEmpty()
                }
            }
    }
}