package cupidcrew.api.backend.exception

enum class BaseResponseCode(val code: Int, val message: String) {
    CREW_NOT_FOUND(404, "Crew not found"),
    INVALID_PASSWORD(400, "Invalid crew password"),
    DUPLICATE_EMAIL(400, "Duplicate crew by email"),
    DUPLICATE_PHONE_NUMBER(400, "Duplicate candidate by phone number"),

    // ... 다른 코드들 ...
}