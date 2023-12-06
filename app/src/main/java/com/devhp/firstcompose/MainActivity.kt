package com.devhp.firstcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devhp.firstcompose.screen.NoteScreen
import com.devhp.firstcompose.ui.theme.FirstComposeTheme
import com.devhp.firstcompose.viewmodel.NoteViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        content()
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent() {
    val noteViewModel: NoteViewModel = viewModel()
    NoteScreen(notes = noteViewModel.noteList, onAddNote = { note ->
        noteViewModel.addNote(note)
    }, onRemoveNote = { note ->
        noteViewModel.removeNote(note)
    })
}
