package cupidcrew.backend.api.service.user

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.dao.user.UserEntity
import cupidcrew.backend.api.model.user.UserLoginRequestModel
import cupidcrew.backend.api.model.user.UserLoginResponseModel
import cupidcrew.backend.api.model.user.UserSignupRequestModel
import cupidcrew.backend.api.model.user.UserSignupResponseModel
import cupidcrew.backend.api.repository.user.UserRepository
import cupidcrew.backend.api.security.JwtTokenProvider
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun findUser(email: String): UserEntity {
        return userRepository.findByEmail(email) ?: throw BaseException(BaseResponseCode.USER_NOT_FOUND)
    }

    fun existsUser(email: String): Boolean {
        if (userRepository.existsByEmail(email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
        return false
    }

    fun createUser(userSignupRequestModel: UserSignupRequestModel): UserSignupResponseModel {
        val user = UserEntity(userSignupRequestModel.name, userSignupRequestModel.email, userSignupRequestModel.password)
        userRepository.save(user)

        return UserSignupResponseModel(user.id!!, user.email)
    }

    fun login(userLoginRequsetModel: UserLoginRequestModel): UserLoginResponseModel {
        val token: String = jwtTokenProvider.createToken(userLoginRequsetModel.email)

        return UserLoginResponseModel(HttpStatus.OK, token)
    }
}
