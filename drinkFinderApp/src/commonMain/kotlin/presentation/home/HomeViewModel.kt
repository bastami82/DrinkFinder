package presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.result.DrinksResultDto
import domain.result.DrinkItemDomainModel
import domain.result.DrinksResultDomainModel
import domain.usecase.GetHomeScreenUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import networking.util.onSuccess

class HomeViewModel(
    private val getHomeScreenUseCase: GetHomeScreenUseCase,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val categories = _homeUiState.asStateFlow()

    suspend fun getHomeData() {
        viewModelScope.launch {

            val latestDrinks = async { getHomeScreenUseCase.getLatestDrinks() }
            val nonAlcoholicDrinks = async { getHomeScreenUseCase.getNonAlcoholicDrinks() }
            val popularDrinks = async { getHomeScreenUseCase.getPopularDrinks() }
            val cocktailGlassDrinks = async { getHomeScreenUseCase.getCocktailGlassDrinks() }
            val champagneFluteDrinks = async { getHomeScreenUseCase.getChampagneFluteDrinks() }

            val combineData: MutableList<DrinksResultDomainModel> = mutableListOf()

            latestDrinks.await().onSuccess {
                combineData.add(it.toDomainModel(title = "Latest"))
            }

            nonAlcoholicDrinks.await().onSuccess {
                combineData.add(it.toDomainModel(title = "Non Alcoholic"))
            }

            popularDrinks.await().onSuccess {
                combineData.add(it.toDomainModel(title = "Popular"))
            }

            cocktailGlassDrinks.await().onSuccess {
                combineData.add(it.toDomainModel(title = "Cocktail Glass"))
            }

            champagneFluteDrinks.await().onSuccess {
                combineData.add(it.toDomainModel(title = "Champagne Flute"))
            }
            _homeUiState.value = HomeUiState.Content(combineData)
        }
    }

    private fun DrinksResultDto.toDomainModel(title: String): DrinksResultDomainModel {
        val modelList: MutableList<DrinkItemDomainModel> = mutableListOf()
        this.drinks.forEach { drinkDto ->
            modelList.add(
                DrinkItemDomainModel(
                    id = drinkDto.idDrink.orEmpty(),
                    name = drinkDto.strDrink.orEmpty(),
                    iconPath = drinkDto.strDrinkThumb.orEmpty(),
                )
            )
        }
        return DrinksResultDomainModel(modelList, title)
    }
}

