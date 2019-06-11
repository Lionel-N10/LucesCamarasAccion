package com.example.aplicacionprueba.firebase

//Este objeto se utiliza para cargar los datos del JSON recibido del Firebase
data class FireBaseData(
    var ListFB: List<ListFB>? = null
)

class ListFB(
    var moviesfirebase: List<Int>? = null,
    var listName: String? = null
)