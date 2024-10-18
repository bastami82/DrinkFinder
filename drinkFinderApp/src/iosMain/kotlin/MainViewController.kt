import androidx.compose.ui.window.ComposeUIViewController
import presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = { di.KoinInitializer().init() }
) { App() }