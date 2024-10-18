package presentation.player

sealed class PlayerUiAction {
    data object OnBackNavigation : PlayerUiAction()
    data class OnVideoUiInteraction(val interaction: String) : PlayerUiAction()
}