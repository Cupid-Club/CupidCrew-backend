package cupidcrew.backend.api.repository.crew

interface CrewRepositoryCustom {

    fun changeBooleanStatus(crewId: Long, fieldName: String, value: Boolean)

}
