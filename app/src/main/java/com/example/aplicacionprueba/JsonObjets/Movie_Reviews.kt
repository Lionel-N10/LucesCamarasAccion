package com.example.aplicacionprueba.JsonObjets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Movie_Reviews {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

}

class Result {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

}