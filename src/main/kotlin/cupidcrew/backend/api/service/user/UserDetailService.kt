package cupidcrew.backend.api.service.user

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.repository.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username) ?: throw BaseException(BaseResponseCode.USER_NOT_FOUND) // username이지만 email인가?
    }
}
