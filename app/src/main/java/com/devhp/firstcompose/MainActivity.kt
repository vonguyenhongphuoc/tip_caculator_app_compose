package com.devhp.firstcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.devhp.firstcompose.screens.QuestionsViewModel
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

    @Composable
    fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()) {
        Questions(viewModel)
    }

    @Composable
    fun Questions(viewModel: QuestionsViewModel) {
        val questions = viewModel.data.value.data?.toMutableList()
        Log.d("INIT SIZE", "Questions: ${questions?.size}")
    }
}

