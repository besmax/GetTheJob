package ru.practicum.android.diploma.core.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.YpBlue
import ru.practicum.android.diploma.core.ui.theme.YpGray
import ru.practicum.android.diploma.core.ui.theme.YsDispalyFamily

@Composable
fun BottomNavBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {

    val screens = listOf(
        Destination.Search,
        Destination.Favorite,
        Destination.Team
    )

    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(android.graphics.Color.parseColor("#E6E8EB")))
                )
                NavigationBar(
                    modifier = Modifier
                        .background(YpGray),
                    tonalElevation = 0.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    screens.forEach { screen ->

                        NavigationBarItem(
                            label = {
                                Text(
                                    text = stringResource(id = screen.titleResId),
                                    fontFamily = YsDispalyFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            },
                            selected = currentRoute == screen.route,
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = screen.iconResId ?: R.drawable.ic_bottom_main
                                    ), contentDescription = "Nav bar icon"
                                )
                            },
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                selectedIconColor = YpBlue,
                                selectedTextColor = YpBlue,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                                indicatorColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }
    )

}
