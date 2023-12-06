package com.devhp.firstcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhp.firstcompose.screen.NoteScreen
import com.devhp.firstcompose.ui.theme.FirstComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                MyApp {
                    Column {
                        MainContent()
                    }
                }

            }
        }
    }
}

@Composable
fun MyApp(
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        content()
    }
}


@Composable
fun MainContent() {
    NoteScreen(notes = emptyList(), onAddNote = {}, onRemoveNote = {})
}
