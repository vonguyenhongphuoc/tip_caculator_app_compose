package com.devhp.firstcompose.screen.search

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devhp.firstcompose.R
import com.devhp.firstcompose.component.InputField
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchScreen(
    navController: NavController,
    viewModel: BookSearchViewModel,
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.navigate(ReaderScreens.HomeScreen.name)
        }
    }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), viewModel = viewModel
                ) { query ->
                    viewModel.searchBooks(query)
                }

                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {

    val listOfBooks = viewModel.list

    Log.d("BooksListView", "Recomposition: ${listOfBooks}")
    val isLoading = viewModel.isLoading.collectAsState().value
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LinearProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }
        }
    }


}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.BookDetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .padding(3.dp), shape = RectangleShape, elevation = CardDefaults.cardElevation(7.dp)) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageUrl = book.volumeInfo?.imageLinks?.smallThumbnail
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
                Text(text = book.volumeInfo?.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author ${book.volumeInfo?.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${book.volumeInfo?.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Date ${book.volumeInfo?.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge
                )

            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotBlank()
    }

    Column {
        InputField(valueState = searchQueryState, labelId = "Search", enabled = true,
            onAction = KeyboardActions {
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}