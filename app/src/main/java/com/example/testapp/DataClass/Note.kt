package com.example.testapp.DataClass

import java.io.Serializable

data class Note(
    var noteId: String = "",
    var content: String = "",
    var timestamp: Long = 0L
) : Serializable
