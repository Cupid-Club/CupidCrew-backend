package cupidcrew.backend.api.exception

enum class BaseResponseCode(val code: Int, val message: String) {
    CREW_NOT_FOUND(400, "Crew not found"),
    CANDIDATE_NOT_FOUND(400, "Candidate not found"),
    INVALID_PASSWORD(400, "Invalid crew password"),
    DUPLICATE_EMAIL(400, "Duplicate crew by email"),
    DUPLICATE_PHONE_NUMBER(400, "Duplicate candidate by phone number"),
    NOT_YET_APPROVED_AS_CREW(400, "Not yet approved as crew"),
    LIMIT_QUALIFICATION_NO_CANDIDATE_REGISTERED(400, "Register at least one candidate to get retrieve qualification"),
    TOKEN_EXPIRED(500, "Token has expired"),
    BAD_CREDENTIALS(400, "Incorrect email or password")
    // ... 다른 코드들 ...
}
