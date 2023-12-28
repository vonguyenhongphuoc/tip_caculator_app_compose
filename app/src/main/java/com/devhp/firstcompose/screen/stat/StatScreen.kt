package com.devhp.firstcompose.screen.stat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devhp.firstcompose.R
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.navigation.ReaderScreens
import com.devhp.firstcompose.screen.home.HomeScreenViewModel
import com.devhp.firstcompose.util.formatDate
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatScreen(navController: NavHostController, homeViewModel: HomeScreenViewModel) {
    val booksData = homeViewModel.data.collectAsState().value.data
    val books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser
    val readingBooks: List<MBook>
    val readBooksList: List<MBook>
    if (!booksData.isNullOrEmpty()) {
        books = booksData.filter { mBook ->
            mBook.userId == currentUser?.uid
        }
        readingBooks = books.filter { mBook ->
            (mBook.startedReading != null) && (mBook.finishedReading == null)
        }
        readBooksList = books.filter { mBook ->
            (mBook.finishedReading != null)
        }
        Scaffold(
            topBar = {
                ReaderAppBar(
                    title = "Book Stats",
                    navController = navController,
                    icon = Icons.Default.ArrowBack,
                    showProfile = false
                ) {
                    navController.popBackStack()
                }
            }
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                Column {
                    Row {
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .padding(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.Person,
                                contentDescription = "person icon"
                            )
                        }
                        // paul
                        Text(
                            text = "Hi, ${
                                currentUser?.email.toString().split("@")[0].uppercase(
                                    Locale.getDefault()
                                )
                            }"
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.titleLarge)
                        Divider()
                        Text(text = "You're reading: ${readingBooks.size} books")
                        Text(text = "You're read: ${readBooksList.size} books")
                    }
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), contentPadding = PaddingValues(16.dp)
                    ) {
                        items(items = readBooksList) { mBook ->
                            BookRowStats(book = mBook, navController = navController)
                        }
                    }
                }

            }


        }
    }
}


@Composable
fun BookRowStats(book: MBook, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.BookDetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .padding(3.dp), shape = RectangleShape, elevation = CardDefaults.cardElevation(7.dp)) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageUrl = book.photoUrl
            AsyncImage(
                model = imageUrl, contentDescription = "book image", placeholder = painterResource(
                    id = R.drawable.ic_android_black_24dp

                ), modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
                error = painterResource(id = R.drawable.baseline_error_24)
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = book.title.toString(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    if (book.rating!! >= 4) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = "Thumbs up icon",
                                tint = Color.Blue.copy(alpha = 0.5f)
                            )
                        }
                    }

                }
                Text(
                    text = "Author ${book.authors}",
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
                Text(

                    text = "Started: ${formatDate(book.startedReading!!)}",
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
                Text(
                    text = "Finished: ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )

            }
        }
    }
}
