package com.devhp.firstcompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devhp.firstcompose.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_tbl")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_tbl WHERE iD =:iD")
    fun getNoteByID(iD: String) : Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note:Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)

    @Query("DELETE FROM note_tbl")
    fun deleteAll()

    @Delete
    fun deleteNote(note:Note)

}