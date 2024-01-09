package ru.practicum.android.diploma.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.ErrorType
import ru.practicum.android.diploma.core.ui.elements.UserInput
import ru.practicum.android.diploma.core.ui.theme.YpBlue
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController,
    setFilterIconActive: (Boolean) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val uiState by viewModel.screenState.observeAsState(initial = SearchScreenState.NotStarted)
    val filterActiveState by viewModel.filtersState.observeAsState(initial = false)

    LaunchedEffect(key1 = Unit) {
        setFilterIconActive(filterActiveState)
    }

    val onUserInputChange: (String) -> Unit = { input ->
        if (input.isBlank()) viewModel.cancelSearch()
        else viewModel.search(input)
    }

    SearchScreenContent(
        uiState = uiState,
        onUserInputChange = onUserInputChange
    )

}

@Composable
fun SearchScreenContent(
    uiState: SearchScreenState,
    onUserInputChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        UserInput(onValueChanged = { onUserInputChange })

        when (uiState) {
            is SearchScreenState.NotStarted -> SearchNotStarted()
            is SearchScreenState.Loading -> SearchLoading()
            is SearchScreenState.Error -> SearchError(uiState.error)
            is SearchScreenState.Content -> SearchContent(uiState.content, uiState.found)
            is SearchScreenState.LoadingNextPage -> SearchLoading()
            is SearchScreenState.LoadingNextPageError -> LoadingPageError(uiState.error)
        }
    }

}

@Composable
fun LoadingPageError(error: ErrorType) {

}

@Composable
fun SearchContent(vacancies: List<Vacancy>, foundNumber: Int) {

}

@Composable
fun SearchError(error: ErrorType) {

}

@Composable
fun SearchLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp),
            color = YpBlue
        )
    }
}

@Composable
fun SearchNotStarted() {
    Image(
        painter = painterResource(id = R.drawable.ph_start_search),
        contentDescription = "Image search not started yet",
        modifier = Modifier.fillMaxSize()
    )
}
