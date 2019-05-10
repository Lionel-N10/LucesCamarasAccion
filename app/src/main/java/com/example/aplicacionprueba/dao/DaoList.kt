package com.example.aplicacionprueba.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.roomobjects.Lista

@Dao
interface DaoList {

    @Query("SELECT * FROM Lista")
    fun getLista(): List<Lista>

    @Query("SELECT NameList FROM Lista")
    fun getNameList(): List<String>

    @Query("SELECT * FROM Lista WHERE UserId = :userid")
    fun getListabyUser(userid: Int): List<Lista>

    @Insert
    fun insertLista(lista: Lista): Long

    @Delete
    fun deleteLista(lista: Lista)
}