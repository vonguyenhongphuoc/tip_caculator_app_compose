package com.devhp.firstcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhp.firstcompose.ui.theme.FirstComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstComposeTheme {
                MyApp {
//                    Column(
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//
//                    }
                    Column {
                        TopHeader()
                    }
                }

            }
        }
    }
}

@Composable
fun MyApp(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        content()
    }
}

@Preview
@Composable
fun TopHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7FE)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Per Person",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "$133.00",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Body() {

    var moneyValue by remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(450.dp)

            .clip(shape = CircleShape.copy(all = CornerSize(12.dp)))
            .border(BorderStroke(2.dp, Color.Black)),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Outline(moneyValue) {
                moneyValue = it
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(text = "Split")
                Spacer(modifier = Modifier.width(200.dp))
                ElevatedCard {
                    Icon(Icons.Filled.Remove, contentDescription = "")
                }
                Text(text = "1", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                ElevatedCard {
                    Icon(Icons.Filled.Add, contentDescription = "")
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(text = "Tip")
                Spacer(modifier = Modifier.width(220.dp))
                Text(text = "$33.0", modifier = Modifier.padding(start = 5.dp, end = 5.dp))
            }
            Box(contentAlignment = Alignment.Center) {
                Text(text = "33%")
            }
            Slider(value = 0F, onValueChange = { value ->
                Log.d("MyTag", value.toString())
            }, steps = 5, valueRange = 0F..50F)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Outline(moneyValue: String, update: (newValue: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.padding(10.dp),
        value = moneyValue,
        onValueChange = { newText ->
            update(newText)
        },
        label = { Text("Enter Bill") },
        leadingIcon = { Icon(Icons.Filled.AttachMoney, contentDescription = "") }
    )
}







