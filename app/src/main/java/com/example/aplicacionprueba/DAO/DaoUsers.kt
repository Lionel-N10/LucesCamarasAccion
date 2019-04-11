package com.example.aplicacionprueba.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.JsonObjets.Users

@Dao
interface DaoUsers {

    @Query("SELECT * FROM Users")
    fun getUsers(): List<Users>

    @Query("SELECT UserName FROM Users WHERE UserId = :UserId")
    fun getNameUsers(UserId: Int): String

    @Insert
    fun insertUser(usuario: Users): Long

    @Delete
    fun deleteUser(usuario: Users)
}