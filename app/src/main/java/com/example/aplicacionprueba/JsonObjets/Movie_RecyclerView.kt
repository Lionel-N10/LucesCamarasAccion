package com.example.aplicacionprueba.JsonObjets

class Movie_RecyclerView(title: String, urlImage: String, genero : String, nota : String, estreno : String) {

    var title : String = ""
    var poster : String
    var genero : String = ""
    var nota : String = ""
    var estreno : String = ""

    init {
        this.title = title
        this.poster = urlImage
        this.estreno = estreno
        this.nota = nota
        this.genero = genero
    }

}


