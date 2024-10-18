package presentation.home

sealed class HomeUiAction {
    data class OnHomeInput(val input: String) : HomeUiAction()
    data class OnDrinkClick(val drinkId: String) : HomeUiAction()
}