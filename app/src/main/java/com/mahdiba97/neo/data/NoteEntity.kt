package com.mahdiba97.neo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahdiba97.neo.NEW_NOTE_ID
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "notes")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var date: Date,
    var text: String
) : Parcelable {
    constructor(text: String) : this(NEW_NOTE_ID, Date(), text)
}