package cupidcrew.backend.api.dao.matching

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "matching")
data class MatchingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name= "senderId", nullable = false)
    var senderId: Long? = null,

    @Column(name= "receiverId", nullable = false)
    var receiverId: Long? = null,

    @Column(name= "senderCandidateId", nullable = false)
    var senderCandidateId: Long? = null,

    @Column(name= "receiverCandidateId", nullable = false)
    var receiverCandidateId: Long? = null,

    @Column(name= "status", nullable = false)
    var status: String,

    @Column(name= "result", nullable = false)
    var result: String,

)
