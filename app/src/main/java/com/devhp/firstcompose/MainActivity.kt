package com.devhp.firstcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.devhp.firstcompose.screens.TriviaHome
import com.devhp.firstcompose.ui.theme.FirstComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TriviaHome()
                }
            }
        }
    }


}

