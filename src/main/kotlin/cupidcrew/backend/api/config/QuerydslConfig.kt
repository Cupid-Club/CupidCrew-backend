//package cupidcrew.backend.api.config
//
//import com.querydsl.jpa.impl.JPAQueryFactory
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import javax.persistence.EntityManager
//import javax.persistence.PersistenceContext
//
//@Configuration
//class QuerydslConfig {
//
//    @PersistenceContext(unitName = "candidateEntityManager")
//    private val candidateEntityManager: EntityManager? = null
//
//    @Bean(name = ["candidateJpaQueryFactory"])
//    fun candidateJpaQueryFactory(): JPAQueryFactory {
//        return JPAQueryFactory(candidateEntityManager)
//    }
//}
