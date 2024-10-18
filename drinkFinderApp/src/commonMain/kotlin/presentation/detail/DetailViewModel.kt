package presentation.detail

import androidx.lifecycle.ViewModel
import data.detail.DrinkDetailDto
import domain.detail.DrinkDetailDomainModel
import domain.usecase.GetDrinkFromIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import networking.util.NetworkError.CONFLICT
import networking.util.NetworkError.MALFORMED_JSON
import networking.util.NetworkError.NO_DATA
import networking.util.NetworkError.NO_INTERNET
import networking.util.NetworkError.PAYLOAD_TOO_LARGE
import networking.util.NetworkError.REQUEST_TIMEOUT
import networking.util.NetworkError.SERIALIZATION
import networking.util.NetworkError.SERVER_ERROR
import networking.util.NetworkError.TOO_MANY_REQUESTS
import networking.util.NetworkError.UNAUTHORIZED
import networking.util.NetworkError.UNKNOWN
import networking.util.onError
import networking.util.onSuccess

class DetailViewModel(
    private val getDrinkFromIdUseCase: GetDrinkFromIdUseCase
) : ViewModel() {
    private val _detailUiState = MutableStateFlow<Detail.UiState>(Detail.UiState.Loading)
    val drink = _detailUiState.asStateFlow()


    suspend fun getDrink(itemId: String) {
            getDrinkFromIdUseCase(itemId).onSuccess {
                _detailUiState.value =
                    Detail.UiState.Content(it.drinks.first().toDetailDomainModel())
            }
                .onError {
                    when (it) {
                        REQUEST_TIMEOUT,
                        UNAUTHORIZED,
                        CONFLICT,
                        TOO_MANY_REQUESTS,
                        PAYLOAD_TOO_LARGE,
                        SERVER_ERROR,
                        SERIALIZATION,
                        UNKNOWN,
                        NO_DATA,
                        MALFORMED_JSON -> _detailUiState.value =
                            Detail.UiState.Error("Something went wrong. \n" +
                                    "Please try again later")

                        NO_INTERNET -> _detailUiState.value =
                            Detail.UiState.Error("Please check your internet connection and try again.")
                    }
                }
    }

    private fun DrinkDetailDto.toDetailDomainModel(): DrinkDetailDomainModel {
        return DrinkDetailDomainModel(
            id = this.idDrink.orEmpty(),
            name = this.strDrink.orEmpty(),
            iconPath = this.strDrinkThumb.orEmpty(),
            ingredients = this.getIngredients(),
            instructions = this.strInstructions.orEmpty(),
        )
    }
}