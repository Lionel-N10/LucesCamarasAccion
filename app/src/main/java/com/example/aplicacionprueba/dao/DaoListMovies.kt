package com.example.aplicacionprueba.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.roomobjects.ListMovies

@Dao
interface DaoListMovies {

    @Query("SELECT MovieId FROM ListMovies WHERE ListId == :listid")
    fun getMovieId(listid: Int): List<Int>

    @Query("SELECT * FROM ListMovies")
    fun getListMovies(): List<ListMovies>

    @Insert
    fun insertLista(listaMovies: ListMovies): Long

    @Delete
    fun deleteLista(listaMovies: ListMovies)
}