package com.devhp.firstcompose.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error
    val loading: LiveData<Boolean> = _loading


    fun resetError() {
        _error.value = ""
    }


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mainScope = MainScope()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d(
            "FB",
            "signInWithEmailAndPassword Exception: localizedMessage - ${exception.localizedMessage} - message: ${exception.message}"
        )
    }

    fun signInUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                try {
                    _loading.postValue(false)
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        _error.postValue(task.result.toString())
                        Log.d("FB", "signInWithEmailAndPassword Failed: ${task.result}")
                    }
                } catch (e: Exception) {
                    _loading.postValue(false)
                    _error.postValue(e.message)
                    Log.d(
                        "FB",
                        "signInWithEmailAndPassword Ex: Message: ${e.message} - MessageLocal: ${e.localizedMessage}"
                    )
                }

            }

        }


    fun createUserWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                try {
                    _loading.postValue(false)
                    if (task.isSuccessful) {
                        val displayName = task.result?.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        onSuccess()
                    } else {
                        _error.postValue(task.result.toString())
                        Log.d("FB", "signInWithEmailAndPassword Failed: ${task.result}")
                    }
                } catch (e: Exception) {
                    _loading.postValue(false)
                    _error.postValue(e.message)
                    Log.d(
                        "FB",
                        "signInWithEmailAndPassword Ex: Message: ${e.message} - MessageLocal: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private fun createUser(displayName: String?) {
        val userID = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()
        user["user_id"] = userID.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance().collection("users").add(user)

    }

}