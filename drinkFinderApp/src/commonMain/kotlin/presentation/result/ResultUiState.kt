package presentation.result

import domain.result.DrinkItemDomainModel

sealed class Result {
    sealed class UiState : Result() {
        data class Content(val data: List<DrinkItemDomainModel>) : UiState()
        data object Loading : UiState()
        data class Error(val message: String?) : UiState()
    }
}