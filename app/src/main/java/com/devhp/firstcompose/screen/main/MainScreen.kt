package com.devhp.firstcompose.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devhp.firstcompose.R
import com.devhp.firstcompose.data.DataOrException
import com.devhp.firstcompose.model.Weather
import com.devhp.firstcompose.model.WeatherItem
import com.devhp.firstcompose.util.formatDate
import com.devhp.firstcompose.util.formatDateTime
import com.devhp.firstcompose.util.formatDecimals
import com.devhp.firstcompose.widget.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    /* Solution 1*/
//    val weatherData = mainViewModel.data.value
    /* Solution 2*/
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(
            null,
            true,
            null
        ), producer = {
            value = mainViewModel.getWeatherData("Moscow")
        }).value
    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weather.city?.name + ",${weather.city?.country}",
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            MainContent(weather)
        }
    }
}

@Composable
fun MainContent(data: Weather) {
    val imageUrl = "https://openweathermap.org/img/wn/${data.list!![0].weather!![0].icon}.png"
    val weatherItem = data.list[0]

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt!!),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.4.sp
            ),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape, color = Color(0XFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp!!.day!!) + "°",
                    style = TextStyle(
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.25.sp
                    )
                )
                Text(
                    text = weatherItem.weather?.get(0)!!.main!!,
                    style = TextStyle(
                        fontStyle = FontStyle.Italic
                    )
                )

            }
        }

        HumidityWindPressureRow(weather = weatherItem)
        Divider()
        Spacer(modifier = Modifier.height(24.dp))
        SunsetSunRiseRow(weather = weatherItem)


    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon", modifier = Modifier.size(34.dp)
            )
            Text(
                text = formatDateTime(weather.sunrise!!),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp
                )
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = formatDateTime(weather.sunset!!),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp
                )
            )

            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset icon",
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon", modifier = Modifier.size(34.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp
                )
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(34.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp
                )
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(34.dp)
            )
            Text(
                text = "${weather.pressure} mph",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp
                )
            )
        }

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "",
        modifier = Modifier.size(80.dp)
    )
}
