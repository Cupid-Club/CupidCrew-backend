package cupidcrew.backend.api.dao.candidate

import cupidcrew.backend.api.dao.crew.CrewEntity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "candidate")
data class CandidateEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "status", nullable = false)
    var status: String = "solo",

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(name = "gender", nullable = false)
    val gender: String,

    @Column(name = "age")
    var age: Int? = 0,

    @Column(name = "height")
    var height: Int? = 0,

    @Column(name = "weight")
    var weight: Int? = 0,

    @Column(name = "address", nullable = false)
    var address: String,

    @Column(name = "job", nullable = false)
    var job: String,

    @Column(name = "mbti", nullable = false)
    var mbti: String,

    @Column(name = "personality", nullable = false)
    var personality: String,

    @Column(name = "idealType", nullable = false)
    var idealType: String,

    @ElementCollection
    @CollectionTable(
        name = "candidate_images",
        joinColumns = [JoinColumn(name = "candidate_id")],
    )
    @Column(name = "imagesUrl")
    var imagesUrl: MutableList<String>? = null,

    @Column(name = "popularity")
    var popularity: Int? = 0,

    @Column(name = "opportunity")
    var opportunity: Int? = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew", nullable = false)
    val crew: CrewEntity
)
