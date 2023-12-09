package com.devhp.firstcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.model.Note
import com.devhp.firstcompose.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {


    /*Có 2 cách để get data khi dùng flow*/
    // Cách 1: Dùng cho trường chúng ta cần xử lý một số logic gì đó trước khi trả về list đưa lên UI
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // distinctUntilChanged: bỏ qua giá trị trùng lặp, tuy nhiên ở đây là dư thừa vì ở trên kia mình đã sử dụng MutableStateFlow rồi
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                val sortedList = listOfNotes.sortedByDescending { it.entryDate }
                _noteList.value = sortedList
            }
        }
    }
    // Cách 2: get trực tiếp data mà không cần xử lý
//    val noteList = repository.getAllNotes()

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { repository.addNote(note) }
    fun updateNote(note: Note) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateNote(note) }

    fun removeNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
//        _noteList.value = _noteList.value.filter { it != note }
    }



}