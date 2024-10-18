package presentation.result

sealed class ResultUiAction {
    data object OnBackNavigation : ResultUiAction()
    data class  OnItemClicked(val itemId: String) : ResultUiAction()
}