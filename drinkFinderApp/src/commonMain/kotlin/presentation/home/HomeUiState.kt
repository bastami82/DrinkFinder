package presentation.home

import domain.result.DrinksResultDomainModel

sealed class HomeUiState {
    data class Content(val items: List<DrinksResultDomainModel>) : HomeUiState()
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
}