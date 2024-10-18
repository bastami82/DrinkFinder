package presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import di.koinViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import presentation.AppDestinations.Detail
import presentation.AppDestinations.Result
import presentation.AppDestinations.Search
import presentation.detail.DetailScreen
import presentation.detail.DetailUiAction
import presentation.home.HomeScreen
import presentation.home.HomeUiAction
import presentation.home.HomeViewModel
import presentation.result.ResultScreen
import presentation.result.ResultUiAction

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Search,
            ) {
                composable<Search> {
                    val homeViewModel = koinViewModel<HomeViewModel>()
                    HomeScreen(
                        homeViewModel.categories.collectAsState()
                    ) { action ->
                        when (action) {
                            is HomeUiAction.OnHomeInput -> {
                                navController.navigate(
                                    Result(
                                        searchTerm = action.input
                                    )
                                )
                            }

                            is HomeUiAction.OnDrinkClick -> {
                                navController.navigate(
                                    Detail(
                                        drinkId = action.drinkId
                                    )
                                )
                            }
                        }
                    }
                }

                composable<Result> {
                    val args = it.toRoute<Result>()
                    ResultScreen(args = args) { action ->
                        when (action) {
                            is ResultUiAction.OnItemClicked -> {
                                navController.navigate(Detail(action.itemId))
                            }

                            is ResultUiAction.OnBackNavigation -> {
                                navController.navigateUp()
                            }
                        }


                    }
                }

                composable<Detail> {
                    val args = it.toRoute<Detail>()
                    DetailScreen(id = args.drinkId) { action ->
                        when (action) {
                            is DetailUiAction.OnBackNavigation -> {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }

}

@Serializable
sealed class AppDestinations {

    @Serializable
    data object Search : AppDestinations()

    @Serializable
    data class Result(
        val searchTerm: String?
    ) : AppDestinations()

    @Serializable
    data class Detail(
        val drinkId: String
    ) : AppDestinations()
}





