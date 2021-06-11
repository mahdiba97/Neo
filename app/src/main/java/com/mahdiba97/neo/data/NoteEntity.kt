package com.mahdiba97.neo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdiba97.neo.NEW_NOTE_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var priority: Int,
    var text: String
) : Parcelable {
    constructor(priority: Int, text: String) : this(NEW_NOTE_ID, priority, text)
}