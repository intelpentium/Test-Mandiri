package kedaiapps.projeku.testmandiri.services.rest

import kedaiapps.projeku.testmandiri.services.Response
import kedaiapps.projeku.testmandiri.services.ResponseResult
import kedaiapps.projeku.testmandiri.services.entity.*
import retrofit2.http.*

interface MainRest {

    //genre
    @Headers("@: Auth")
    @GET("genre/movie/list")
    suspend fun genre() : Response<List<ResponseGenre>>

    //movie
    @Headers("@: Auth")
    @GET("discover/movie")
    suspend fun movie(
        @Query("page") page: Int,
        @Query("with_genres") with_genres: Int,
//        @Query("search") search: String,
    ) : ResponseMovie

    //movie detail
    @Headers("@: Auth")
    @GET("movie/{movie_id}")
    suspend fun movieDetail(
        @Path("movie_id") movie_id: Int,
    ) : ResponseMovieItem

    //review
    @Headers("@: Auth")
    @GET("movie/{movie_id}/reviews")
    suspend fun review(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
    ) : ResponseReview

    //youtube
    @Headers("@: Auth")
    @GET("movie/{movie_id}/videos")
    suspend fun youtube(
        @Path("movie_id") movie_id: Int,
    ) : ResponseResult<List<ResponseYoutube>>


//    //search
//    @GET("games")
//    suspend fun search(
//        @Query("search") search: String,
//        @Query("key") key: String,
//    ) : Response<List<ResponseHome>>
//
//    //search
//    @GET("products/{id}")
//    suspend fun homeDetail(
//        @Path("id") id: String,
//        @Query("key") key: String,
//    ) : ResponseHomeDetail
}