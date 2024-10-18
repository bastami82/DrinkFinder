package presentation.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import presentation.home.HomeUiAction

@OptIn(FlowPreview::class)
@Composable
fun ThrottledTextField(action: (HomeUiAction) -> Unit, maxWidth: Dp) {
    var text by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val textFlow = remember { MutableStateFlow("") }

    LaunchedEffect(textFlow) {
        textFlow
            .debounce(1500) // one and half seconds
            .filterNot {
                it.isBlank()
            }
            .collect { throttledText ->
                action(HomeUiAction.OnHomeInput(throttledText))
            }
    }
    OutlinedTextField(
        modifier = Modifier.width(if (maxWidth >= 700.dp) (maxWidth / 2) else maxWidth)
            .padding(horizontal = 16.dp),
        value = text,
        onValueChange = {
            text = it
            coroutineScope.launch {
                textFlow.emit(it)
            }
        },
        label = { Text("🔍 Drink name or ingredient") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
    )
}