package com.mahdiba97.notes

import com.mahdiba97.notes.data.DateConverter
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.data.ServerNoteEntity
import java.util.*

const val NEW_NOTE_ID = 0
const val NOTE_TEXT_KEY = "noteTextKey"
const val CURSOR_POSITION_KEY = "cursorPositionKey"
const val SELECTED_NOTE_KEY = "selectedNoteKey"
const val URL = "http://10.0.2.2:9000/"

fun converterToNoteEntity(items: List<ServerNoteEntity>?): List<NoteEntity> {
    val notes = mutableListOf<NoteEntity>()
    val dateConverter = DateConverter()
    var date: Date
    for (i in items!!) {
        date = dateConverter.fromTimestamp(i.date)
        notes.add(NoteEntity(i.id, date, i.text))
    }
    return notes
}

fun converterToServerNoteEntity(items: List<NoteEntity>?): List<ServerNoteEntity> {
    val notes = mutableListOf<ServerNoteEntity>()
    val dateConverter = DateConverter()
    var date: Long
    for (i in items!!) {
        date = dateConverter.dateToTimestamp(i.date)
        notes.add(ServerNoteEntity(i.id, date, i.text))
    }
    return notes
}
