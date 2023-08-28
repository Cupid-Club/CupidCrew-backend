package cupidcrew.backend.api.dao.candidate

import cupidcrew.backend.api.dao.crew.CrewEntity
import javax.persistence.*

@Entity
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
    @Column(name = "images")
    var images: MutableList<String>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crewid", nullable = false)
    val crew: CrewEntity
)
