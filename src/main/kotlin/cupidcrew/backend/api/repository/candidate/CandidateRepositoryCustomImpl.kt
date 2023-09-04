package cupidcrew.backend.api.repository.candidate

import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.candidate.QCandidateEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
class CandidateRepositoryCustomImpl(
    @Autowired val queryFactory: JPAQueryFactory,
) : CandidateRepositoryCustom {

    @Transactional
    override fun increaseField(candidateId: Long, fieldName: String) {
        val candidate = QCandidateEntity.candidateEntity
        val field = PathBuilder(CandidateEntity::class.java, candidate.metadata.name)
            .getNumber(fieldName, Long::class.java)

        queryFactory.update(candidate)
            .set(field, field.add(1))
            .where(candidate.id.eq(candidateId))
            .execute()
    }
}
