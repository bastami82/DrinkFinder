package presentation.result

import androidx.lifecycle.ViewModel
import data.result.DrinksResultDto
import domain.result.DrinkItemDomainModel
import domain.usecase.GetDrinksFromSearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import networking.util.NetworkError
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

class ResultViewModel(
    private val getResultsForSearchUseCase: GetDrinksFromSearchUseCase
) :
    ViewModel() {
    private val _resultUiState = MutableStateFlow<Result.UiState>(Result.UiState.Loading)
    val drinks = _resultUiState.asStateFlow()

    suspend fun getDrinksForSearch(searchInput: String) =
        try {
            getResultsForSearchUseCase(searchTerm = searchInput)
                .onSuccess {
                    _resultUiState.value = Result.UiState.Content(it.toResultsDomainModel())
                }
                .onError {
                    showError(it)
                }
        } catch (e: Exception) {
            showError(NO_DATA)
        }


    private fun showError(it: NetworkError) {
        when (it) {
            REQUEST_TIMEOUT,
            UNAUTHORIZED,
            CONFLICT,
            TOO_MANY_REQUESTS,
            PAYLOAD_TOO_LARGE,
            SERVER_ERROR,
            SERIALIZATION,
            UNKNOWN,
            MALFORMED_JSON -> _resultUiState.value = Result.UiState.Error(
                "Something went wrong. \n" +
                        "Please try again later"
            )

            NO_INTERNET -> _resultUiState.value =
                Result.UiState.Error(
                    "Please check your internet connection and try again."
                )

            NO_DATA -> _resultUiState.value = Result.UiState.Error(
                "No result! \n" +
                        "Please try again with different search key."
            )
        }
    }
}

fun DrinksResultDto.toResultsDomainModel(): List<DrinkItemDomainModel> =
    drinks.mapNotNull { (drinkId, drinkName, drinkThumb) ->
        drinkName?.let { name ->
            drinkThumb?.let { iconPath ->
                drinkId?.let { id ->
                    DrinkItemDomainModel(
                        id = id,
                        name = name,
                        iconPath = iconPath
                    )
                }
            }
        }
    }