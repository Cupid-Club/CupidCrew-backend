package cupidcrew.backend.api.exception

enum class BaseResponseCode(val code: Int, val message: String) {
    CREW_NOT_FOUND(404, "Crew not found"),
    CANDIDATE_NOT_FOUND(404, "Candidate not found"),
    INVALID_PASSWORD(400, "Invalid crew password"),
    DUPLICATE_EMAIL(400, "Duplicate crew by email"),
    DUPLICATE_PHONE_NUMBER(400, "Duplicate candidate by phone number"),
    LIMIT_QUALIFICATION_NO_CANDIDATE_REGISTERED(400, "Register at least one candidate to get retrieve qualification")
    // ... 다른 코드들 ...
}
