package domain.detail

import data.detail.DrinksDetailDto
import networking.util.AppNetworkResult
import networking.util.NetworkError

interface DetailRepository {
   suspend  fun getDrink(id:String): AppNetworkResult<DrinksDetailDto, NetworkError>
}