package com.devhp.firstcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devhp.firstcompose.navigation.ReaderNavigation
import com.devhp.firstcompose.ui.theme.FirstComposeTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
//                val db = FirebaseFirestore.getInstance()
//                val user: MutableMap<String, Any> = HashMap()
//                user["firstName"] = "Jeo"
//                user["lastName"] = "James"
//                db.collection("users").add(user).addOnSuccessListener {
//                    Log.d("MyTag", "Create user success: ${it.id}")
//                }.addOnFailureListener {
//                    Log.d("MyTag", "Create user failure: ${it.localizedMessage}")
//                }
                ReaderApp()

            }
        }
    }

    @Composable
    private fun ReaderApp() {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReaderNavigation()
            }
        }
    }
}
