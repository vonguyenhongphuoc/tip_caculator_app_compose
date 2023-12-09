package com.devhp.firstcompose.repository

import com.devhp.firstcompose.data.NoteDao
import com.devhp.firstcompose.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {
    suspend fun addNote(note: Note) = noteDao.insert(note)
    suspend fun updateNote(note: Note) = noteDao.update(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDao.deleteAll()
    fun getAllNotes(): Flow<List<Note>> = noteDao.getNotes().flowOn(Dispatchers.IO).conflate()
//    fun getAllNotes(): Flow<List<Note>> = noteDao.getNotes()
    fun getNoteById(iD: String): Flow<Note> =
        noteDao.getNoteByID(iD).flowOn(Dispatchers.IO).conflate()


}