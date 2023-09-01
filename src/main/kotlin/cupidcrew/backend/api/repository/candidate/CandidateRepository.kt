package cupidcrew.backend.api.repository.candidate

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
class CandidateRepository(
    private val candidateJpaRepository: CandidateJpaRepository,
    @Qualifier("candidateRepositoryCustomImpl") private val customRepository: CandidateRepositoryCustom,
) : CandidateJpaRepository by candidateJpaRepository, CandidateRepositoryCustom by customRepository
