package com.example.lucescamarasaccion


import com.example.aplicacionprueba.JsonObjets.GenresResponse
import com.example.aplicacionprueba.JsonObjets.MovieDetails_Object
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesClient {

    @GET("movie/top_rated")
    fun GetTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<Movies>

    @GET("movie/upcoming")
    fun GetUpcoming(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<Movies>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<Movies>

    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path ( "movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ):Call<MovieDetails_Object>


    @GET("search/movie")
    fun getMovieByTitle(
        @Query ("api_key") api_key: String,
        @Query ("query") query: String
    ):Call<Movies>


    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresResponse>


    //https://api.github.com/users/Lionel-N10/repos
    //https://api.themoviedb.org/3/movie/top_rated?api_key=39534c06f3f59b461ca70b61f782f06d&language=es-ES&page=1
}


/*interface MoviesClient {


}*/