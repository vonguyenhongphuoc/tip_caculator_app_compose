package com.devhp.firstcompose.screen.update

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devhp.firstcompose.R
import com.devhp.firstcompose.component.InputField
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.component.RoundedButton
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.util.formatDate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookUpdateScreen(
    navController: NavController,
    updateViewModel: UpdateViewModel,
    documentID: String
) {
    LaunchedEffect(key1 = Unit, block = {
        updateViewModel.getBookByDocumentID(documentID)
    })
    val context = LocalContext.current
    val bookInfo = updateViewModel.data.collectAsState().value
    val notesState = rememberSaveable(bookInfo.data?.notes) {
        mutableStateOf(bookInfo.data?.notes ?: "No thoughts available :(")
    }
    val isStartedReading = rememberSaveable {
        mutableStateOf(false)
    }
    val isFinishedReading = rememberSaveable {
        mutableStateOf(false)
    }

    val ratingVal = remember {
        mutableIntStateOf(0)
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Update Book",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        if (bookInfo.data == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Surface(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BookInfoArea(bookInfo)
                    Spacer(modifier = Modifier.height(15.dp))
                    BookNotesForm(notesState)
                    Spacer(modifier = Modifier.height(15.dp))
                    UpdateReadingAndFinishedArea(bookInfo, isStartedReading, isFinishedReading)
                    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
                    bookInfo.data?.rating?.toInt().let {
                        RatingBar(rating = it!!) { rating ->
                            ratingVal.intValue = rating
                            Log.d("MyTag", "Show star: ${ratingVal.intValue}")
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        val changeNotes = bookInfo.data?.notes != notesState.value
                        val changedRating = bookInfo.data?.rating?.toInt() != ratingVal.intValue
                        val isFinishedTimeStamp =
                            if (isFinishedReading.value) Timestamp.now() else bookInfo.data?.finishedReading
                        val isStartedTimeStamp =
                            if (isStartedReading.value) Timestamp.now() else bookInfo.data?.startedReading

                        val bookUpdate =
                            changeNotes || changedRating || isStartedReading.value || isFinishedReading.value

                        val bookToUpdate = hashMapOf(
                            "finished_reading_at" to isFinishedTimeStamp,
                            "started_reading_at" to isStartedTimeStamp,
                            "rating" to ratingVal.intValue,
                            "notes" to notesState.value
                        ).toMap()

                        RoundedButton(label = "Update") {
                            if (bookUpdate) {
                                FirebaseFirestore.getInstance().collection("books")
                                    .document(bookInfo.data!!.id!!)
                                    .update(bookToUpdate)
                                    .addOnCompleteListener { task ->
                                        showToast(context, "Book Updated Successfully")
                                        Log.d("MyTag", "ShowSimpleForm: ${task.result}")
                                        navController.popBackStack()
                                    }.addOnFailureListener {
                                        Log.d("Error", "Error updating document", it)
                                    }
                            }
                        }
                        Spacer(modifier = Modifier.width(100.dp))

                        Log.d("MyTag opendialog", "${openDialog.value}")

                        if (openDialog.value) {
                            ShowAlertDialog(
                                message = stringResource(id = R.string.sure) + "\n" + stringResource(
                                    id = R.string.action
                                ),
                                openDialog = openDialog,
                                onYesPressed = {
                                    FirebaseFirestore.getInstance().collection("books")
                                        .document(bookInfo.data!!.id!!).delete()
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                openDialog.value = false
                                                navController.popBackStack()
                                            }
                                        }
                                        .addOnFailureListener {
                                            Log.d("Firebase Error: ", it.message.toString())
                                        }
                                }
                            )
                        }

                        RoundedButton(label = "Delete") {
                            openDialog.value = true
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun ShowAlertDialog(message: String, openDialog: MutableState<Boolean>, onYesPressed: () -> Unit) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = { onYesPressed.invoke() }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(text = "No")
                }
            },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) })
    }

}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onPressRating: (Int) -> Unit
) {
    var ratingState by remember {
        mutableIntStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy), label = ""
    )

    Row(
        modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_star_outline_24),
                contentDescription = "star icon",
                modifier = modifier
                    .size(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }

                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }

    }


}


@Composable
fun UpdateReadingAndFinishedArea(
    bookInfo: DataOrException<MBook, Boolean, Exception>,
    isStartedReading: MutableState<Boolean>,
    isFinishedReading: MutableState<Boolean>
) {
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(onClick = {
            isStartedReading.value = true
        }, enabled = bookInfo.data?.startedReading == null) {
            if (bookInfo.data?.startedReading == null) {
                if (!isStartedReading.value) {
                    Text(text = "Start Reading")
                } else {
                    Text(
                        text = "Started Reading!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text(text = "Started on: ${formatDate(bookInfo.data?.startedReading!!)}")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = { isFinishedReading.value = true },
            enabled = bookInfo.data?.finishedReading == null
        ) {
            if (bookInfo.data?.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading!")
                }
            } else {
                Text(text = "Finished on: ${formatDate(bookInfo.data?.finishedReading!!)}")
            }
        }
    }
}


@Composable
fun BookNotesForm(
    valueState: MutableState<String>
) {
    InputField(
        valueState = valueState,
        labelId = "Notes",
        isSingleLine = false
    )
}

@Composable
fun BookInfoArea(bookInfo: DataOrException<MBook, Boolean, Exception>) {
    val bookData = bookInfo.data
    Card(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(48.dp),
        elevation = CardDefaults.cardElevation(24.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = bookData?.photoUrl.toString(),
                contentDescription = "Image Book",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.baseline_error_24),
                modifier = Modifier
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .widthIn(min = 50.dp, max = 200.dp)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "${bookData?.title}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${bookData?.authors}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${bookData?.publishedDate}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}



