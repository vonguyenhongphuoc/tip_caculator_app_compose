package com.devhp.firstcompose.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavHostController) {
    Text(text = "Home Screen - ${FirebaseAuth.getInstance().currentUser?.email}")
}