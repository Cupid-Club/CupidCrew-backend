package cupidcrew.backend.api.repository.crew

import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.candidate.QCandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dao.crew.QCrewEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.transaction.Transactional


@Repository
class CrewRepositoryCustomImpl(
    @Autowired val queryFactory: JPAQueryFactory,
) : CrewRepositoryCustom {

    @Transactional
    override fun changeBooleanStatus(crewId: Long, fieldName: String, status: Boolean) {
        val crew = QCrewEntity.crewEntity
        val field = PathBuilder(CrewEntity::class.java, crew.metadata.name)
                .getBoolean(fieldName)

        queryFactory
                .update(crew)
                .set(field, status)
                .where(crew.crewid.eq(crewId))
                .execute()
    }

    @Transactional
    override fun resetPassword(crewId: Long, encodedNewPassword: String) {
        val crew = QCrewEntity.crewEntity
        queryFactory
            .update(crew)
            .set(crew.m_password, encodedNewPassword)
            .where(crew.crewid.eq(crewId))
            .execute()
    }

}
