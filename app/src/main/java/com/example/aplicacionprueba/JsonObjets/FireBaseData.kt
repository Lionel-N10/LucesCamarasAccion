package com.example.aplicacionprueba.JsonObjets


data class FireBaseData(
    var ListFB: List<ListFB>? = null,
    var userName: String? = null,
    var userPass: String? = null
)/*{
    constructor() : this(null, "", "" )
}*/

class ListFB(
    var moviesFB: List<Int>? = null,
    var listId: Int? = null,
    var listName: String? = null
)/*{
    constructor(): this(null, null, "")
}*/