package ru.practicum.android.diploma.core.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.practicum.android.diploma.core.ui.navigation.Destination
import ru.practicum.android.diploma.core.ui.theme.GetTheJobTheme

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()

        setContent {
            GetTheJobTheme {
                val navController = rememberNavController()
                var bottomNavBarIsVisible = rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Destination.Search.route -> bottomNavBarIsVisible.value = true
                }
            }
        }
    }

    private fun setLocale() {
        val localeList = LocaleListCompat.forLanguageTags("ru")
        AppCompatDelegate.setApplicationLocales(localeList)
    }

}



