package presentation.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil3.CoilImage
import di.koinViewModel
import domain.result.DrinkItemDomainModel
import drinkfinder.drinkfinderapp.generated.resources.Res
import drinkfinder.drinkfinderapp.generated.resources.empty_list
import org.jetbrains.compose.resources.painterResource
import presentation.AppDestinations.Result
import presentation.result.Result.UiState

@Composable
fun ResultScreen(args: Result, action: (ResultUiAction) -> Unit) {

    val resultViewModel = koinViewModel<ResultViewModel>()
    resultViewModel.drinks.collectAsState().value.let { state ->
        Box {
            when (state) {
                is UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(text = "Loading .... ")
                        LinearProgressIndicator()
                    }
                }

                is UiState.Content -> ShowDrinks(
                    state.data,
                    action
                )

                is UiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.empty_list),
                            contentDescription = "empty list"
                        )
                        Text(
                            text = state.message
                                ?: "Something went wrong! we are so sorry 🥺 \nPlease try again later."
                        )
                    }
                }
            }
            // back button
            IconButton(
                onClick = { action(ResultUiAction.OnBackNavigation) }
            )
            {
                Icon(
                    contentDescription = "back",
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                        .padding(8.dp)
                )
            }
        }

    }

    LaunchedEffect(Unit) {
        val searchTerm = args.searchTerm
        searchTerm?.let {
            resultViewModel.getDrinksForSearch(searchTerm)
        }
            ?: throw IllegalArgumentException("Missing arguments, Search Term must be passed to the Result screen")
    }
}

@Composable
fun ShowDrinks(
    content: List<DrinkItemDomainModel>,
    action: (ResultUiAction) -> Unit
) {
    BoxWithConstraints {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (maxWidth >= 700.dp) 4 else 2),
            modifier = Modifier
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(content.size) { index ->
                val drink = content.elementAt(index)
                IconButton(
                    modifier = Modifier
                        .fillMaxSize(),
                    onClick = {
                        action(ResultUiAction.OnItemClicked(drink.id))
                    },
                ) {

                    Column(
                        horizontalAlignment = CenterHorizontally,
                    ) {
                        CoilImage(
                            modifier = Modifier.clip(RoundedCornerShape(15.dp)),
                            imageModel = { drink.iconPath },
                        )
                        Text(
                            text = drink.name.trim(),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }

}