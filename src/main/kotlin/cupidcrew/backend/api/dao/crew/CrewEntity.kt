package cupidcrew.backend.api.dao.crew

import cupidcrew.backend.api.dao.BaseTime
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "crew")
class CrewEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(name = "m_password", nullable = false)
    var m_password: String
) : BaseTime(), UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var crewid: Long? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return m_password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
