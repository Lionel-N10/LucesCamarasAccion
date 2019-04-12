package com.example.aplicacionprueba.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.JsonObjets.ListMovies
import com.example.aplicacionprueba.JsonObjets.Lista

@Dao
interface DaoListMovies {

    @Query("SELECT MovieId FROM ListMovies")
    fun getMovieId(): List<Int>

    @Query("SELECT * FROM ListMovies")
    fun getListMovies(): List<ListMovies>

    @Insert
    fun insertLista(listaMovies: ListMovies): Long

    @Delete
    fun deleteLista(listaMovies: ListMovies)
}