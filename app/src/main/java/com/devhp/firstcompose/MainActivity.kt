package com.devhp.firstcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhp.firstcompose.model.Portfolio
import com.devhp.firstcompose.ui.theme.FirstComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCreateBizCard() {
    CreateBizCard()
}


@Composable
fun CreateBizCard() {
    val isVisiblePortfolios = remember {
        mutableStateOf(false)
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile()
                Divider()
                CreateInfo()
                Button(onClick = {
                    isVisiblePortfolios.value = !isVisiblePortfolios.value
                }) {
                    Text(text = "Portfolio")
                }
                if (isVisiblePortfolios.value) {
                    val portfolios = listOf(
                        Portfolio(1, "Project 1", "A great project Indeed"),
                        Portfolio(2, "Project 2", "A great project Indeed"),
                        Portfolio(3, "Project 3", "A great project Indeed"),
                        Portfolio(4, "Project 4", "A great project Indeed"),
                        Portfolio(5, "Project 5", "A great project Indeed"),
                        Portfolio(6, "Project 6", "A great project Indeed")
                    )
                    PortfolioList(portfolios)
                }

            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPortfolioList() {
    val portfolios = listOf(
        Portfolio(1, "Project 1", "A great project Indeed"),
        Portfolio(2, "Project 2", "A great project Indeed"),
        Portfolio(3, "Project 3", "A great project Indeed"),
        Portfolio(4, "Project 4", "A great project Indeed"),
        Portfolio(5, "Project 5", "A great project Indeed"),
        Portfolio(6, "Project 6", "A great project Indeed")
    )
    PortfolioList(portfolios)
}


@Composable
fun PortfolioList(portfolios: List<Portfolio>) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        LazyColumn(verticalArrangement = Arrangement.SpaceAround) {
            items(portfolios) { portfolio ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            Toast
                                .makeText(context, portfolio.projectName, Toast.LENGTH_LONG)
                                .show()
                        },
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color.Transparent)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CreateImageProfile(modifier = Modifier.size(200.dp))
                        Column {
                            Text(
                                text = portfolio.projectName,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                            Text(text = portfolio.description)
                        }

                    }
                }
            }
        }
    }


}

@Composable
private fun CreateInfo() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Miles P.",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Android Compsoe Programmer",
            modifier = Modifier.padding(3.dp)

        )
        Text(
            text = "@themilesCompose",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(3.dp)
        )
    }
}

@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {

        Image(
            painterResource(id = R.drawable.user_profile),
            contentDescription = "profile image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )


    }
}
