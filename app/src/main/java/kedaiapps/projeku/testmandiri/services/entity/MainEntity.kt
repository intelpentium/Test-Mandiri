package kedaiapps.projeku.testmandiri.services.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

//genre
@Keep
data class ResponseGenre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)


//movie
@Keep
data class ResponseMovie(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<ResponseMovieItem>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
)

@Keep
data class ResponseMovieItem(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("vote_average") val vote_average: String,
    @SerializedName("vote_count") val vote_count: String,
    @SerializedName("overview") val overview: String,
)


//review
@Keep
data class ResponseReview(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<ResponseReviewDetail>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int,
)

@Keep
data class ResponseReviewDetail(
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("author_details") val author_details: ResponseReviewItem,
)

@Keep
data class ResponseReviewItem(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatar_path: String,
    @SerializedName("rating") val rating: Int,
)


//youtube
@Keep
data class ResponseYoutube(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
)


