package cupidcrew.backend.api.model.crew

import com.fasterxml.jackson.annotation.JsonProperty

data class CrewSignupRequestModel(
    val name: String,
    val email: String,
    var password: String,
    val mutualFriend: String,
    val company: String,
    @JsonProperty("isApproved")
    val isApproved: Boolean?= false,
)
