package cupidcrew.backend.api.config

import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value

@Configuration
class MinioConfig {

    @Value("\${minio.url}")
    private val url: String? = null

    @Value("\${minio.accessKey}")
    private val accessKey: String? = null

    @Value("\${minio.secretKey}")
    private val secretKey: String? = null

    @Bean
    fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint(url!!)
            .credentials(accessKey!!, secretKey!!)
            .build()
    }
}