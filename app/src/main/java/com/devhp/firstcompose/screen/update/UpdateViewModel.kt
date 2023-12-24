package com.devhp.firstcompose.screen.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateViewModel @Inject constructor(private val firebaseRepository: FireRepository) :
    ViewModel() {
    private val _data = MutableStateFlow(DataOrException<MBook, Boolean, Exception>())
    val data: StateFlow<DataOrException<MBook, Boolean, Exception>> = _data

    suspend fun getBookByDocumentID(documentID: String){
        viewModelScope.launch(Dispatchers.IO) {
            _data.value.loading = true
            _data.value = firebaseRepository.getBooksByDocumentID(documentID)
            if(_data.value.data != null) _data.value.loading = false
            Log.d("MyTag", "getBookByDocumentID: ${_data.value.data}")
        }
    }


}