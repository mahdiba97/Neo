package com.mahdiba97.neo.data

data class NoteEntity(
    val id: Int,
    val priority: Int,
    val text: String
) {
    constructor(priority: Int, text: String) : this(0, priority, text)
}