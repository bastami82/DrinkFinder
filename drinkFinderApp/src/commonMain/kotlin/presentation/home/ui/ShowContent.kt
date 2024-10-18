package presentation.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil3.CoilImage
import domain.result.DrinkItemDomainModel
import domain.result.DrinksResultDomainModel
import drinkfinder.drinkfinderapp.generated.resources.Res
import drinkfinder.drinkfinderapp.generated.resources.icon_home_app
import org.jetbrains.compose.resources.painterResource
import presentation.home.HomeUiAction

@Composable
fun ShowContent(
    content: List<DrinksResultDomainModel>,
    lazyListState: LazyListState,
    nestedScrollConnection: NestedScrollConnection,
    action: (HomeUiAction) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .nestedScroll(nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        state = lazyListState
    ) {
        itemsIndexed(content) { _, category ->
            Text(
                text = category.title,
                fontSize = 22.sp,
                style = TextStyle(fontWeight = FontWeight.SemiBold),
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(category.drinks) { _, drink ->
                    ItemCard(action, drink)
                }
            }
        }
    }
}

@Composable
private fun ItemCard(
    action: (HomeUiAction) -> Unit,
    drink: DrinkItemDomainModel
) {
    Row(modifier = Modifier.width(185.dp).height(220.dp)) {
        IconButton(
            onClick = { action(HomeUiAction.OnDrinkClick(drink.id)) }
        ) {
            Column(
                horizontalAlignment = CenterHorizontally
            ) {
                CoilImage(
                    modifier = Modifier.semantics {
                        contentDescription = "Drink Image for ${drink.name}"
                    },
                    previewPlaceholder = painterResource(Res.drawable.icon_home_app)  ,
                    imageModel = { drink.iconPath },
                    loading = {
                        Image(
                            painter = painterResource(Res.drawable.icon_home_app),
                            contentDescription = "Image loading",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = drink.name,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}