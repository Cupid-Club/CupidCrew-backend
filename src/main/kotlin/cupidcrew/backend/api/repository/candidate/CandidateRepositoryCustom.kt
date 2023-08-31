package cupidcrew.backend.api.repository.candidate

interface CandidateRepositoryCustom {

    fun increaseField(candidateId: Long, fieldName: String)
}
