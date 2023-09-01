package cupidcrew.backend.api.model.candidate

data class CandidateInfoResponseModel(
    var status: String? = "solo",
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
    var images: MutableList<String>? = null,
    var popularity: Int? = 0,
    var opportunity: Int? = 0,
    var crew: String? = null
)
