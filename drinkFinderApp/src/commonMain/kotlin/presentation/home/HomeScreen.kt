package presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import di.koinViewModel
import drinkfinder.drinkfinderapp.generated.resources.Res
import drinkfinder.drinkfinderapp.generated.resources.home_error
import org.jetbrains.compose.resources.imageResource
import presentation.home.ui.Banner
import presentation.home.ui.SearchInputField
import presentation.home.ui.ShowContent

@Composable
fun HomeScreen(state: State<HomeUiState>, action: (HomeUiAction) -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()
    val shouldLoadData = rememberSaveable { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()
    val logoScrollSpeed = 0.9f

    var logoOffset by remember {
        mutableStateOf(0f)
    }

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val layoutInfo = lazyListState.layoutInfo
            // Check if the first item is visible
            if(lazyListState.firstVisibleItemIndex == 0) {
                return Offset.Zero
            }
            if(layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1) {
                return Offset.Zero
            }
            logoOffset += delta * logoScrollSpeed
            return Offset.Zero
        }
    }
    BoxWithConstraints {
        state.value.let { stateItem ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = CenterHorizontally
            ) {
                Banner(
                    Modifier.fillMaxWidth()
                        .clipToBounds()
                        .heightIn(min = 40.dp, max = 120.dp)
                        .height(120.dp + logoOffset.toDp())
                )
                SearchInputField(action, this@BoxWithConstraints.maxWidth)

                Content(stateItem, lazyListState, nestedScrollConnection, action)
            }
        }
    }
    LaunchedEffect(shouldLoadData.value) {
        shouldLoadData.value.takeIf { it }?.let {
            viewModel.getHomeData()
            shouldLoadData.value = false
        }
    }
}

@Composable
private fun Float.toDp(): Dp = (this / LocalDensity.current.density).dp

@Composable
fun Content(
    stateItem: HomeUiState,
    lazyListState: LazyListState,
    nestedScrollConnection: NestedScrollConnection,
    action: (HomeUiAction) -> Unit
) {
    when (stateItem) {
        is HomeUiState.Content -> {
            if (stateItem.items.isNotEmpty()) {
                ShowContent(stateItem.items, lazyListState, nestedScrollConnection, action)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Image(
                        bitmap = imageResource(Res.drawable.home_error),
                        contentDescription = "no category found"
                    )
                }
            }
        }

        is HomeUiState.Loading -> {
            ShowContentLoading()
        }

        is HomeUiState.Error -> {
            showError()
        }
    }
}

@Composable
private fun showError() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            bitmap = imageResource(Res.drawable.home_error),
            contentDescription = "Error loading home content"
        )
    }
}

@Composable
fun ShowContentLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading .... ")
        LinearProgressIndicator()
    }
}
