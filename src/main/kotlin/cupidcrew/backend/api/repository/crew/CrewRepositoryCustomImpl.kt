package cupidcrew.backend.api.repository.crew

import com.querydsl.jpa.impl.JPAQueryFactory
import cupidcrew.backend.api.dao.crew.QCrewEntity
import org.springframework.beans.factory.annotation.Autowired

class CrewRepositoryCustomImpl(
    @Autowired val queryFactory: JPAQueryFactory,
) : CrewRepositoryCustom {

    val qCrew = QCrewEntity.crewEntity
}
