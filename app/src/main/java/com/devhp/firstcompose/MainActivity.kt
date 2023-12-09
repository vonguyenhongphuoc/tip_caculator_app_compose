package com.devhp.firstcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devhp.firstcompose.screen.NoteScreen
import com.devhp.firstcompose.ui.theme.FirstComposeTheme
import com.devhp.firstcompose.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                MyApp {
                    Column {
                        val noteViewModel: NoteViewModel = viewModel()
                        MainContent(noteViewModel)
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



@Composable
fun MainContent(noteViewModel: NoteViewModel) {
    val noteList = noteViewModel.noteList.collectAsState().value
    NoteScreen(notes = noteList, onAddNote = { note ->
        noteViewModel.addNote(note)
    }, onRemoveNote = { note ->
        noteViewModel.removeNote(note)
    })
}
