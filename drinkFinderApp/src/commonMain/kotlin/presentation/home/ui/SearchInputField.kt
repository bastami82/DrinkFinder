package presentation.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import presentation.home.HomeUiAction

@Composable
fun SearchInputField(
    action: (HomeUiAction) -> Unit,
    maxWidth: Dp,
) {
    ThrottledTextField(action, maxWidth)
}