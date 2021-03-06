package com.example.aplicacionprueba.retrofit


import com.example.aplicacionprueba.jsonobjects.GenresResponse
import com.example.aplicacionprueba.jsonobjects.MovieDetails_Object
import com.example.aplicacionprueba.jsonobjects.MovieVideos_Object
import com.example.lucescamarasaccion.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//La interface contiene todos los metodos para realizar las consultas
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

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<Movies>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieVideos_Object>

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

}

