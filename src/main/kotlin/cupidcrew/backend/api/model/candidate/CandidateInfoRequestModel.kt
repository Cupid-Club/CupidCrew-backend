package cupidcrew.backend.api.model.candidate

import org.springframework.web.multipart.MultipartFile

data class CandidateInfoRequestModel(
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
    var images: MutableList<String>? = null,
    var crewid: Long? = null
)
