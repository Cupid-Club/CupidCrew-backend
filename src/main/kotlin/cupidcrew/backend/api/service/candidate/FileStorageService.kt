package cupidcrew.backend.api.service.candidate

import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.time.Instant

@Service
class FileStorageService(private val minioClient: MinioClient) {

    @Value("\${minio.url}")
    private val url: String? = null

    @Value("\${minio.bucketName}")
    private val bucketName: String? = null

    fun uploadFiles(imageList: List<MultipartFile>): List<String> {
        val urlList = mutableListOf<String>()
        println("[upload service] $urlList")
        imageList.forEach { file ->
            val inputStream: InputStream = file.inputStream
            
            val originalFilename = file.originalFilename ?: "file"
            val extension = originalFilename.substringAfterLast(".", "")
            val nameWithoutExtension = originalFilename.substringBeforeLast(".")
            val objectName = "$nameWithoutExtension-${Instant.now().toEpochMilli()}.$extension"

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName!!)
                    .`object`(objectName)
                    .stream(inputStream, file.size, -1)
                    .contentType(file.contentType)
                    .build()
            )

            val url = "$url/$bucketName/$objectName"

            urlList.add(url)
        }

        return urlList
    }

}