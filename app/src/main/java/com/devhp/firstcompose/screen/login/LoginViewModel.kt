package com.devhp.firstcompose.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mainScope = MainScope()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(
            "FB",
            "signInWithEmailAndPassword Exception: localizedMessage - ${exception.localizedMessage} - message: ${exception.message}"
        )
    }

    fun signInUserWithEmailAndPassword(email: String, password: String) =
        viewModelScope.launch {
            _loading.value = true
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FB", "signInWithEmailAndPassword Success: ${task.result}")
                    mainScope.launch {
                        _loading.value = false
                    }
                } else {
                    Log.d("FB", "signInWithEmailAndPassword Failed: ${task.result}")
                }
            }

        }

    fun createUserWithEmailAndPassword(userName: String, password: String) {

    }

}