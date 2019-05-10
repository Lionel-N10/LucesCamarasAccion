package com.example.aplicacionprueba.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val BASE_URL = "https://api.themoviedb.org/3/" // Url base que servira para todas las llamadas a la API

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()

    //Con el Interceptor podemos recoger el estado de la consulta. Obtenemos tanto el tiempo de respuesta como el objeto JSON y si la llamada de realiza correctamente o no.
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder()

    //Creamos el servicio con el interceptor y la conexion a la API
    fun <S> createService(serviceClass: Class<S>): S {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)

            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}
