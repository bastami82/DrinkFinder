package networking.util

enum class NetworkError : AppError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    MALFORMED_JSON,
    NO_DATA,
    UNKNOWN;
}