package ru.practicum.android.diploma.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.practicum.android.diploma.search.ui.SearchScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    setFilterIconActive: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Search.route
    ) {
        composable(route = Destination.Search.route) {
            SearchScreen(
                navController = navController,
                setFilterIconActive
            )
        }
    }
}
