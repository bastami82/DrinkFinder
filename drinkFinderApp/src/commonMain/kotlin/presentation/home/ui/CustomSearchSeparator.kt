package presentation.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchSeparator(maxWidth: Dp) {
    Column(
        modifier = Modifier.width(if (maxWidth >= 700.dp) (maxWidth / 2) else maxWidth),
        horizontalAlignment = CenterHorizontally
    ) {
        Box(contentAlignment = Center) {
            Divider(
                color = Color.Red, thickness = 2.dp, modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp).background(Color.White),
                text = "  Or  "
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 16.dp), text = "Choose By Category"
        )
    }
}