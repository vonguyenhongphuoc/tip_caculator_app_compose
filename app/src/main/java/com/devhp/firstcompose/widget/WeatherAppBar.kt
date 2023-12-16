package com.devhp.firstcompose.widget

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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

    val paddingTop = remember {
        mutableStateOf(0.dp)
    }

    val expanded = remember {
        mutableStateOf(false)
    }

    val density = LocalDensity.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(
            showDialog = showDialog,
            navController = navController,
            paddingTop = paddingTop,
            expanded = expanded
        )
    }

    TopAppBar(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                paddingTop.value = with(density) { coordinates.size.height.toDp() + 5.dp }
            }
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
                    expanded.value = true
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
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController,
    paddingTop: MutableState<Dp>,
    expanded: MutableState<Boolean>
) {

    val items = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = paddingTop.value, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
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
                        expanded.value = false
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
