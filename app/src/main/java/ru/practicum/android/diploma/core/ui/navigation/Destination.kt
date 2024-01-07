package ru.practicum.android.diploma.core.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class Destination(val route: String, @StringRes val titleResId: Int, @DrawableRes val iconResId: Int? = null) {
    data object Search : Destination(route = "search", titleResId = R.string.search, iconResId = R.drawable.ic_bottom_main)
    data object Favorite : Destination(route = "favorite", titleResId = R.string.favorite, iconResId = R.drawable.ic_bottom_favorite)
    data object Team : Destination(route = "team", titleResId = R.string.team, iconResId = R.drawable.ic_bottom_team)

}
