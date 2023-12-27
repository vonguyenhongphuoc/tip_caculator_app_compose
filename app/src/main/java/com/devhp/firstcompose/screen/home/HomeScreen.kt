package com.devhp.firstcompose.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devhp.firstcompose.component.FABContent
import com.devhp.firstcompose.component.ListCard
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.component.TitleSection
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeScreenViewModel) {

//    LaunchedEffect(key1 = Unit, block = {
//        viewModel.getAllBooksFromDatabase()
//    })

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            Log.d("MyTag", "HomeScreen Cleared")
        }
    })
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.BookSearchScreen.name)
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HomeContent(navController = navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController, viewModel: HomeScreenViewModel) {

    val data = viewModel.data.collectAsState().value
    val listOfBooks: List<MBook>
    val currentUser = remember {
        FirebaseAuth.getInstance().currentUser
    }
    val columScrollState = rememberScrollState()

    if (data.loading!!) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        listOfBooks = data.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        val email = FirebaseAuth.getInstance().currentUser?.email
        val currentUserName = if (!email.isNullOrEmpty()) email.split("@")[0] else "N/A"

        Column(Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceAround) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleSection(label = "Your reading \n" + " activity right now...")
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(ReaderScreens.StatScreen.name)
                            }
                            .size(45.dp),
                        tint = MaterialTheme.colorScheme.secondaryContainer
                    )
                    Text(
                        text = currentUserName, modifier = Modifier.padding(2.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Red, fontSize = 15.sp, maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                    Divider(modifier = Modifier.width(45.dp))

                }

            }

            Column(
                modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(columScrollState)
            ) {
                ReadingRightNowArea(books = listOfBooks, navController = navController)
            }

            TitleSection(label = "Reading List")


            BookListArea(listOfBooks = listOfBooks, navController = navController)

        }
    }


}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavHostController) {
    val readingNowList = books.filter {
        mBook -> mBook.startedReading != null && mBook.finishedReading == null
    }
    for (book in readingNowList) {
        ListCard(book) {
            navController.navigate(ReaderScreens.BookUpdateScreen.name + "/$it")
        }
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavHostController) {
    val addedBooks = listOfBooks.filter {
        mBook -> mBook.startedReading == null && mBook.finishedReading == null
    }

    HorizontalScrollComponent(addedBooks) {
        navController.navigate(ReaderScreens.BookUpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}

