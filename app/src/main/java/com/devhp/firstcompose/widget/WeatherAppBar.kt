package com.devhp.firstcompose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    navController: NavController,
    icon: ImageVector? = null,
    navIcon: ImageVector = Icons.Default.ArrowBack,
    isMainScreen: Boolean = true,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        modifier = Modifier
            .padding(10.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                modifier = Modifier.padding(start = 20.dp)
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Icon",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        navigationIcon = {
            if (!isMainScreen) {
                Icon(
                    imageVector = navIcon,
                    contentDescription = "Navigation Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            fontWeight = FontWeight.W300
                        )
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false

                    },
                    leadingIcon = {
                        Icon(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            }, contentDescription = null, tint = Color.LightGray
                        )
                    })

            }
        }

    }
}
