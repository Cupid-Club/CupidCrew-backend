package cupidcrew.api.backend.exception

enum class BaseResponseCode(val code: Int, val message: String) {
    USER_NOT_FOUND(404, "User not found"),
    INVALID_PASSWORD(400, "Invalid password"),
    DUPLICATE_EMAIL(400, "Duplicate email"),

    // ... 다른 코드들 ...
}