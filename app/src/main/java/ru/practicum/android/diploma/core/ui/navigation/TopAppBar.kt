package ru.practicum.android.diploma.core.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes titleResId: Int,
    navigateBack: () -> Unit,
    arrowState: Boolean,
    onFilterIconClick: () -> Unit,
    filterIconVisibleState: Boolean,
    filterIconActiveState: Boolean,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text(stringResource(titleResId))
        },
        navigationIcon = {
            if (arrowState)
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back icon"
                    )
                }
        },
        actions = {
            IconButton(onClick = { onFilterIconClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filters_on),
                    contentDescription = "Filter icon"
                )
            }
        }
    )
}
