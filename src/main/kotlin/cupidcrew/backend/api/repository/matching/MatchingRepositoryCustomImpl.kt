package cupidcrew.backend.api.repository.matching

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MatchingRepositoryCustomImpl(
    @Autowired val queryFactory: JPAQueryFactory,
) : MatchingRepositoryCustom {

}