package com.example.aplicacionprueba.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.aplicacionprueba.JsonObjets.Users

@Dao
interface DaoUser {

    @Query("SELECT * FROM Users")
    fun getUsers(): List<Users>

    @Query("SELECT UserName FROM Users")
    fun getAllUsersName(): List<String>

    @Query("SELECT UserName FROM Users WHERE UserId = :userId")
    fun getUserName(userId: Int): String

    @Insert
    fun insertUser(user: Users): Long

    @Delete
    fun deleteUser(user: Users)
}