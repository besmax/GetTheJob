package ru.practicum.android.diploma.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.practicum.android.diploma.core.ui.navigation.BottomNavBar
import ru.practicum.android.diploma.core.ui.navigation.Destination
import ru.practicum.android.diploma.core.ui.navigation.NavigationGraph
import ru.practicum.android.diploma.core.ui.navigation.TopBar
import ru.practicum.android.diploma.core.ui.theme.GetTheJobTheme

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()

        setContent {
            GetTheJobTheme {
                val navController = rememberNavController()
                val screenTitle = rememberSaveable { mutableIntStateOf(Destination.Search.titleResId) }
                var backIconIsVisible by rememberSaveable { mutableStateOf(false) }
                var filterIconVisibleState by rememberSaveable { mutableStateOf(false) }
                var filterIconActiveState by rememberSaveable { mutableStateOf(false) }
                var bottomNavBarIsVisible by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Destination.Search.route -> {
                        screenTitle.intValue = Destination.Search.titleResId
                        backIconIsVisible = false
                        filterIconVisibleState = true
                        bottomNavBarIsVisible = true
                    }

                    Destination.Favorite.route -> {
                        screenTitle.intValue = Destination.Favorite.titleResId
                        backIconIsVisible = false
                        filterIconVisibleState = false
                        bottomNavBarIsVisible = true
                    }

                    Destination.Team.route -> {
                        screenTitle.intValue = Destination.Team.titleResId
                        backIconIsVisible = false
                        filterIconVisibleState = false
                        bottomNavBarIsVisible = true
                    }

                    else -> {
                        bottomNavBarIsVisible = false
                        filterIconVisibleState = false
                        backIconIsVisible = true
                    }
                }

                Scaffold(
                    topBar = {
                        TopBar(
                            titleResId = screenTitle.intValue,
                            navigateBack = { navController.navigateUp() },
                            arrowState = backIconIsVisible,
                            filterIconVisibleState = filterIconVisibleState,
                            filterIconActiveState = filterIconActiveState,
                            onFilterIconClick = {  }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,
                            state = bottomNavBarIsVisible,
                            modifier = Modifier
                        )
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(
                            navController = navController,
                            setFilterIconActive = { isActive -> filterIconActiveState = isActive }
                        )
                    }
                }
            }
        }
    }

    private fun setLocale() {
        val localeList = LocaleListCompat.forLanguageTags("ru")
        AppCompatDelegate.setApplicationLocales(localeList)
    }

}



