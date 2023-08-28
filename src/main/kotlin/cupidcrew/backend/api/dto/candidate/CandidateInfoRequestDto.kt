package cupidcrew.backend.api.dto.candidate

data class CandidateInfoRequestDto(
    var status: String = "solo",
    val phoneNumber: String,
    val gender: String,
    var age: Int? = 0,
    var height: Int? = 0,
    var weight: Int? = 0,
    var address: String,
    var job: String,
    var mbti: String,
    var personality: String,
    var idealType: String,
    var imagePaths: MutableList<String>,
    var crewid: Long,
)
