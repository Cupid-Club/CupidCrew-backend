package cupidcrew.backend.api.mapper.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.crew.CrewFindIdResponseDto
import cupidcrew.backend.api.dto.crew.CrewFirebaseTokenResponseDto
import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.model.crew.CrewFireBaseTokenResponseModel
import cupidcrew.backend.api.model.crew.CrewSignupRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupResponseModel
import org.mapstruct.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class CrewMapper {

    // signup
    abstract fun toDto(model: CrewSignupRequestModel): CrewSignupRequestDto

    @Mapping(target = "role", qualifiedByName = ["mapRole"])
    abstract fun toEntity(dto: CrewSignupRequestDto): CrewEntity
    @Named("mapRole")
    fun mapRole(role: String): SimpleGrantedAuthority = SimpleGrantedAuthority(role)

    @Mapping(target = "role", qualifiedByName = ["mapRoleToString"])
    abstract fun toDto(entity: CrewEntity): CrewSignupRequestDto

    @Named("mapRoleToString")
    fun mapRoleToString(role: SimpleGrantedAuthority): String = role.authority
    
    abstract fun toModel(dto: CrewSignupRequestDto) : CrewSignupResponseModel

    abstract fun toDtoFindId(entity: CrewEntity) : CrewFindIdResponseDto

    abstract fun toDtoFirebaseToken(entity: CrewEntity) : CrewFirebaseTokenResponseDto

    abstract fun toModelFireBaseToken(dto: CrewFirebaseTokenResponseDto) : CrewFireBaseTokenResponseModel

}
