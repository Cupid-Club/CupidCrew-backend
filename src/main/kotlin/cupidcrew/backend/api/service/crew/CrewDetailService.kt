package cupidcrew.backend.api.service.crew

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.repository.crew.CrewRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

//@Service
//class CrewDetailService(private val crewRepository: CrewRepository) : UserDetailsService {
//    override fun loadUserByUsername(username: String): UserDetails {
//        return crewRepository.findByEmail(username) ?: throw BaseException(BaseResponseCode.CREW_NOT_FOUND) // username이지만 email인가?
//    }
//}
