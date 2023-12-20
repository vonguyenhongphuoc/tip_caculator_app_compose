package com.devhp.firstcompose.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.devhp.firstcompose.R
import com.devhp.firstcompose.component.FABContent
import com.devhp.firstcompose.component.ListCard
import com.devhp.firstcompose.component.ReaderAppBar
import com.devhp.firstcompose.component.TitleSection
import com.devhp.firstcompose.model.MBook
import com.devhp.firstcompose.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = { FABContent(onTap = {}) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            HomeContent(navController = navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController) {

    val listOfBooks = listOf(
        MBook("asdf", "Hello Again", "All of us", null),
        MBook("asddwqd", "Hello", "All of us", null),
        MBook("sfsdfsdf", "Again", "All of us", null),
        MBook("ad121sdf", "Again Again", "All of us", null),
        )

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

        ReadingRightNowArea(books = listOf<MBook>(), navController = navController)

        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavHostController) {
    ListCard(onPressDetails = {})
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavHostController) {
    HorizontalScrollComponent(listOfBooks) {

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
            ListCard {
                onCardPressed(it)
            }
        }
    }
}

