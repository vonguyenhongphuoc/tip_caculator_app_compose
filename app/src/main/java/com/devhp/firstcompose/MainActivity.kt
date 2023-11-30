package com.devhp.firstcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.devhp.firstcompose.navigation.MovieNavigation
import com.devhp.firstcompose.ui.theme.FirstComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                MyApp {
                    MovieNavigation()
                }

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    content()
}




