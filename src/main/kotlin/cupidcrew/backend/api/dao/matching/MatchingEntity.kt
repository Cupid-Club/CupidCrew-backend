package cupidcrew.backend.api.dao.matching

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*


@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "matching")
data class MatchingEntity(
    @Column(name= "senderId", nullable = false)
    val senderId: Long,

    @Column(name= "receiverId", nullable = false)
    val receiverId: Long,

    @Column(name= "senderCandidateId", nullable = false)
    val senderCandidateId: Long,

    @Column(name= "receiverCandidateId", nullable = false)
    val receiverCandidateId: Long,

    @Column(name= "status", nullable = false)
    var status: String? = "unchecked",

    @Column(name= "result", nullable = false)
    var result: String? = "unmatched,"

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
