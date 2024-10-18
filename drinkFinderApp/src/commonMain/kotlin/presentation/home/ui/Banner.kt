package presentation.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import drinkfinder.drinkfinderapp.generated.resources.Res
import drinkfinder.drinkfinderapp.generated.resources.drink_finder
import drinkfinder.drinkfinderapp.generated.resources.icon_home_app
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Banner(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Image(
            bitmap = imageResource(Res.drawable.drink_finder),
            contentDescription = "Drink Finder",
            colorFilter = ColorFilter.tint(Color.Red),
        )
        Image(
            painter = painterResource(Res.drawable.icon_home_app),
            contentDescription = "app logo",
        )
    }


}