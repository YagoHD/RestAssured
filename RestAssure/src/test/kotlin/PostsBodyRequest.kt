import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class PostsBodyRequest(@Required val userId: Int, @Required val id: Int, val title: String, val body: String)
