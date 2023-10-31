package cupidcrew.backend.api.repository.matching

import cupidcrew.backend.api.repository.candidate.CandidateJpaRepository
import cupidcrew.backend.api.repository.candidate.CandidateRepositoryCustom
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
class MatchingRepository(
    private val matchingJpaRepository: MatchingJpaRepository,
    @Qualifier("matchingRepositoryCustomImpl") private val customRepository: MatchingRepositoryCustom,
) : MatchingJpaRepository by matchingJpaRepository, MatchingRepositoryCustom by customRepository