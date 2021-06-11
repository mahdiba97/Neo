package mahdiba97.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahdiba97.neo.data.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(notes: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER BY priority ASC")
    fun getAll(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): NoteEntity?

    @Query("SELECT COUNT(*) FROM notes")
    fun getCount(): Int

    @Delete
    fun deleteNotes(selectedNotes: List<NoteEntity>): Int

    @Query("DELETE From notes")
    fun deleteAllNotes(): Int

    @Delete
    fun deleteNote(it: NoteEntity): Int
}