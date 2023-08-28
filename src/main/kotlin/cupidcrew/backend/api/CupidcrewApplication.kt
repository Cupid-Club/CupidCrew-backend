package cupidcrew.backend.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class CupidcrewApplication

fun main(args: Array<String>) {
    runApplication<CupidcrewApplication>(*args)
}
