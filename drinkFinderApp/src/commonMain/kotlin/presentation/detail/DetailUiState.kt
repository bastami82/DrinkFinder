package presentation.detail

import domain.detail.DrinkDetailDomainModel

sealed class Detail {
    sealed class UiState : Detail() {
        data class Content(val value: DrinkDetailDomainModel) : UiState()
        data object Loading : UiState()
        data class Error(val message: String? = null) : UiState()
    }
}