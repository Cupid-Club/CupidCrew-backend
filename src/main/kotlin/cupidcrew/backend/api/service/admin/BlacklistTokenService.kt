package cupidcrew.backend.api.service.admin

import org.springframework.stereotype.Service
import java.util.*

@Service
class BlacklistTokenService {

    private val blacklistedTokens = Collections.synchronizedSet(mutableSetOf<String>())

    fun blacklist(token: String) {
        blacklistedTokens.add(token)
    }

    fun isBlacklisted(token: String): Boolean {
        return blacklistedTokens.contains(token)
    }
}