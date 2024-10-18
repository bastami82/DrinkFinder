package networking

import data.detail.DrinksDetailDto
import data.result.DrinksResultDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import isDebugBuild
import networking.util.AppNetworkResult
import networking.util.NetworkError
import uk.appyapp.GlobalConfig

private const val BASE_URL = "https://www.thecocktaildb.com"
private val URL = when (isDebugBuild()) {
    false -> "$BASE_URL${GlobalConfig.API_URL}"
    else -> "$BASE_URL/api/json/v2/1/"
}


class DrinksService(
    private val httpClient: HttpClient,
) {
    private suspend inline fun <reified T> makeRequest(url: String): AppNetworkResult<T, NetworkError> {
        return try {
            handleResponse(httpClient.get(url))
        } catch (e: Exception) {
            AppNetworkResult.Error(NetworkError.UNKNOWN)
        }

    }

    private suspend inline fun <reified T> handleResponse(response: HttpResponse): AppNetworkResult<T, NetworkError> =
        when (response.status.value) {
            in 200..299 -> AppNetworkResult.Success(response.body<T>())
            401 -> AppNetworkResult.Error(NetworkError.UNAUTHORIZED)
            408 -> AppNetworkResult.Error(NetworkError.REQUEST_TIMEOUT)
            409 -> AppNetworkResult.Error(NetworkError.CONFLICT)
            413 -> AppNetworkResult.Error(NetworkError.PAYLOAD_TOO_LARGE)
            429 -> AppNetworkResult.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> AppNetworkResult.Error(NetworkError.SERVER_ERROR)
            else -> AppNetworkResult.Error(NetworkError.UNKNOWN)
        }

    suspend fun getLatestDrinks(): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}latest.php")

    suspend fun getPopularDrinks(): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}popular.php")

    suspend fun getNonAlcoholicDrinks(): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}filter.php?a=Non_Alcoholic")

    suspend fun getCocktailGlassDrinks(): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}filter.php?g=Cocktail_glass")

    suspend fun getChampagneFluteDrinks(): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}filter.php?g=Champagne_flute")

    suspend fun getDrinksFromSearch(searchTerm: String): AppNetworkResult<DrinksResultDto, NetworkError> =
        makeRequest("${URL}search.php?s=$searchTerm")

    suspend fun getDrinkFromId(drinkId: String): AppNetworkResult<DrinksDetailDto, NetworkError> =
        makeRequest("${URL}lookup.php?i=$drinkId")
}