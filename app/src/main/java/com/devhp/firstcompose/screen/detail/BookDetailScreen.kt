package com.devhp.firstcompose.screen.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devhp.firstcompose.R
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.component.RoundedButton
import com.devhp.firstcompose.data.Resource
import com.devhp.firstcompose.model.Item
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.navigation.ReaderScreens
import com.devhp.firstcompose.screen.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    bookID: String,
    viewModel: DetailsViewModel,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = true, block =
    {
        Log.d("BookDetailScreen", "BookID: $bookID")
        sharedViewModel.getBookInfo(bookID)
    })

    val bookInfo = sharedViewModel.bookInfo.collectAsState().value

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Books Details",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                showProfile = false
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bookInfo.data == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row {
                            LinearProgressIndicator()
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(text = "Loading...")
                        }
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)
                }

            }

        }
    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookID = bookInfo.data?.id
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {

        Card(
            modifier = Modifier.padding(34.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            AsyncImage(
                model = bookData?.imageLinks?.thumbnail,
                contentDescription = "book image",
                placeholder = painterResource(
                    id = R.drawable.ic_android_black_24dp

                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp),
                error = painterResource(id = R.drawable.baseline_error_24)
            )


        }

        Text(
            text = bookData?.title.toString(),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "Authors: ${bookData?.authors?.joinToString()}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )


        Text(
            text = "Page Count: ${bookData?.pageCount.toString()}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )


        Text(
            text = "Categories: ${bookData?.categories?.joinToString()}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3
        )


        Text(
            text = "Published: ${bookData?.publishedDate.toString()}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(5.dp))

        val cleanDescription =
            HtmlCompat.fromHtml(bookData?.description!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
                .toString()
        val localDims = LocalContext.current.resources.displayMetrics
        Surface(
            modifier = Modifier
                .height(localDims.heightPixels.dp.times(0.09f))
                .padding(4.dp),
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {

            LazyColumn(modifier = Modifier.padding(3.dp)) {
                item {
                    Text(text = cleanDescription)
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RoundedButton(label = "Save") {
                val book = MBook(
                    title = bookData.title,
                    authors = bookData.authors?.joinToString(),
                    description = bookData.description,
                    categories = bookData.categories?.joinToString(),
                    notes = "",
                    photoUrl = bookData.imageLinks?.thumbnail,
                    publishedDate = bookData.publishedDate,
                    pageCount = bookData.pageCount.toString(),
                    rating = 0.0,
                    googleBookId = googleBookID,
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                )
                saveToFirebase(book, navController)
            }
            Spacer(modifier = Modifier.width(25.dp))
            RoundedButton(label = "Cancel") {
                navController.popBackStack()
            }
        }


    }

}


private fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    dbCollection.add(book).addOnSuccessListener { documentRef ->
        val docID = documentRef.id
        dbCollection.document(documentRef.id).update(hashMapOf("id" to docID) as Map<String, Any>)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.popBackStack()
                }
            }.addOnFailureListener {
                Log.e("Error", "SaveToFirebase: Error updating doc", it)
            }

    }

}
