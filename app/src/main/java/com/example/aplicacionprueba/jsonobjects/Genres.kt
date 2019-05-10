package com.example.aplicacionprueba.jsonobjects

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Genres {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("name")
    @Expose
    var name: String? = null
}