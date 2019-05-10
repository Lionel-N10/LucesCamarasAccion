package com.example.aplicacionprueba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aplicacionprueba.dao.DaoList
import com.example.aplicacionprueba.dao.DaoListMovies
import com.example.aplicacionprueba.dao.DaoUser
import com.example.aplicacionprueba.roomobjects.ListMovies
import com.example.aplicacionprueba.roomobjects.Lista
import com.example.aplicacionprueba.roomobjects.Users

//Esta clase se encarga de controlar la base de datos interna a traves de la libreria Room

//Le pasamos los objetos(Tablas) y asignamos la version. Cada cambio en las tablas debe reflejarse con una nueva versión.
@Database(entities = [Lista::class, Users::class, ListMovies::class], version = 6, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    //funciones con la interaccion a la base de datos(Insert, Update, Delete...)
    abstract fun DaoMovies(): DaoListMovies

    abstract fun DaoList(): DaoList
    abstract fun DaoUser(): DaoUser

    companion object {
        @Volatile
        private var instance: DataBase? = null
        private val LOCK = Any()

        //Creamos el constructor de la base de datos y la configuramos para que se elimine y se genere de nuevo cuando migremos a una nueva version
        private fun Build(context: Context) = Room.databaseBuilder(context, DataBase::class.java, "database.db").fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: Build(context).also { instance = it }
        }
    }
}