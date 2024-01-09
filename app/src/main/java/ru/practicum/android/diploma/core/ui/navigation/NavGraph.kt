package ru.practicum.android.diploma.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.practicum.android.diploma.search.ui.SearchScreen
import ru.practicum.android.diploma.vacancydetails.ui.VacancyDetailsScreen

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
        composable(
            route = Destination.VacancyDetails.route,
            arguments = listOf(
                navArgument(name = "vacancyId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            val vacancyId = it.arguments?.getString("vacancyId") ?: ""
            VacancyDetailsScreen(vacancyId = vacancyId, navController = navController)
        }
    }
}
