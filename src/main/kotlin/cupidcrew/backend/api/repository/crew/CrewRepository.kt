package cupidcrew.backend.api.repository.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.repository.candidate.CandidateJpaRepository
import cupidcrew.backend.api.repository.candidate.CandidateRepositoryCustom
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class CrewRepository(
        private val crewJpaRepository: CrewJpaRepository,
        @Qualifier("crewRepositoryCustomImpl") private val customRepository: CrewRepositoryCustom,
) : CrewJpaRepository by crewJpaRepository, CrewRepositoryCustom by customRepository