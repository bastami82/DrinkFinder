package presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil3.CoilImage
import di.koinViewModel
import domain.detail.DrinkDetailDomainModel
import drinkfinder.drinkfinderapp.generated.resources.Res
import drinkfinder.drinkfinderapp.generated.resources.empty_list
import org.jetbrains.compose.resources.painterResource

@Composable
fun DetailScreen(id: String?, action: (DetailUiAction) -> Unit) {
    val detailViewModel = koinViewModel<DetailViewModel>()
    detailViewModel.drink.collectAsState().value.let { state ->
        Box {
            when (state) {
                is Detail.UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Loading .... ")
                        LinearProgressIndicator()
                    }
                }

            is Detail.UiState.Content -> ShowDrink(
                state.value, action
            )

                is Detail.UiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(Res.drawable.empty_list),
                            contentDescription = "error")
                        Text(text = state.message ?: "Something went wrong! we are so sorry 🥺 \nPlease try again later.")
                    }
                }
            }
            //back button
            IconButton(
                onClick = { action(DetailUiAction.OnBackNavigation) }
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
        id?.let {
            detailViewModel.getDrink(id)
        }
            ?: throw IllegalArgumentException("Missing argument! drinkId must be passed to the Detail screen")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowDrink(value: DrinkDetailDomainModel, action: (DetailUiAction) -> Unit) {
    BoxWithConstraints {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            CoilImage(
                imageModel = { value.iconPath },
                modifier = Modifier.width(if (this@BoxWithConstraints.maxWidth >= 700.dp) this@BoxWithConstraints.maxWidth / 1.5f else this@BoxWithConstraints.maxWidth)
                    .clip(
                        RoundedCornerShape(
                            topStart = 25.dp, topEnd = 25.dp
                        )
                    ).drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(
                                    0.85F to Color.White.copy(alpha = 0F), 1F to Color.White
                                )
                            )
                        }
                    }
                    .aspectRatio(if (this@BoxWithConstraints.maxWidth >= 700.dp) (16f / 9f) else 1f)
            )
            Text(
                modifier = Modifier.fillMaxWidth().offset(y = (-20).dp),
                fontSize = 30.sp,
                text = value.name,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
            )
//            if (value.youtubeLink.isNotBlank()) {
//                Text(
//                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
//                        .background(Color.Red.copy(alpha = 0.2f)),
//                    text = "Video:",
//                    fontSize = 30.sp,
//                    textAlign = TextAlign.Start,
//                    fontFamily = FontFamily.Cursive
//                )
//
//                IconButton(
//                    onClick = {
//                        action(DetailUiAction.OnVideoItemClick(value.youtubeLink))
//                    }) {
//                    Image(
//                        modifier = Modifier.width(300.dp)
//                            .aspectRatio(16f / 9f),
//                        painter = painterResource(Res.drawable.play_icon),
//                        contentDescription = ""
//                    )
//                }
//
//            }

            value.ingredients.takeIf { it.isNotEmpty() }?.let {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .background(Color.Red.copy(alpha = 0.2f)),
                    text = "Ingredients:",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Cursive
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = (if (this@BoxWithConstraints.maxWidth >= 700.dp) Int.MAX_VALUE else 1),
                ) {
                    it.forEach { ingredient ->
                        Row(Modifier.padding(horizontal = 16.dp)) {
                            Text(
                                text = ingredient.first,
                                fontSize = 14.sp,
                            )
                            Text(
                                text = " : ",
                                fontSize = 16.sp,
                            )
                            Text(
                                text = ingredient.second,
                                fontSize = 14.sp,
                            )
                            Text(
                                text = ", ",
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (value.instructions.isNotBlank()) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .background(Color.Red.copy(alpha = 0.2f)),
                    text = "Recipe:",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Cursive
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = value.instructions,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
