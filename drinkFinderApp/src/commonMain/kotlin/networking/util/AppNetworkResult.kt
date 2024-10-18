package networking.util


sealed interface AppNetworkResult<out D, out E : AppError> {
    data class Success<out D>(val data: D) : AppNetworkResult<D, Nothing>
    data class Error<out E : AppError>(val error: E) : AppNetworkResult<Nothing, E>
}

inline fun <T, E : AppError, R> AppNetworkResult<T, E>.map(map: (T) -> R): AppNetworkResult<R, E> {
    return when (this) {
        is AppNetworkResult.Error -> AppNetworkResult.Error(error)
        is AppNetworkResult.Success -> AppNetworkResult.Success(map(data))
    }
}

fun <T, E : AppError> AppNetworkResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : AppError> AppNetworkResult<T, E>.onSuccess(action: (T) -> Unit): AppNetworkResult<T, E> {
    return when (this) {
        is AppNetworkResult.Error -> this
        is AppNetworkResult.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : AppError> AppNetworkResult<T, E>.onError(action: (E) -> Unit): AppNetworkResult<T, E> {
    return when (this) {
        is AppNetworkResult.Error -> {
            action(error)
            this
        }

        is AppNetworkResult.Success -> this
    }
}

typealias EmptyResult<E> = AppNetworkResult<Unit, E>
