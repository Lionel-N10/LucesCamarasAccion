package com.example.lucescamarasaccion


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

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<Movies>




    //https://api.github.com/users/Lionel-N10/repos
    //https://api.themoviedb.org/3/movie/top_rated?api_key=39534c06f3f59b461ca70b61f782f06d&language=es-ES&page=1
}


/*interface MoviesClient {


}*/