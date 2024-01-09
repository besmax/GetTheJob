package ru.practicum.android.diploma.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.ErrorType
import ru.practicum.android.diploma.core.ui.elements.UserInput
import ru.practicum.android.diploma.core.ui.elements.VacancyList
import ru.practicum.android.diploma.core.ui.navigation.Destination
import ru.practicum.android.diploma.core.ui.theme.YpBlue
import ru.practicum.android.diploma.core.ui.theme.YsDispalyFamily
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
        onUserInputChange = onUserInputChange,
        onVacancyClick = { vacancyId ->
            navController.navigate(
                Destination.VacancyDetails.route.replace(
                    "{id}",
                    vacancyId
                )
            )
        }
    )

}

@Composable
fun SearchScreenContent(
    uiState: SearchScreenState,
    onUserInputChange: (String) -> Unit,
    onVacancyClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        UserInput(onValueChanged = onUserInputChange)

        when (uiState) {
            is SearchScreenState.NotStarted -> SearchNotStarted()
            is SearchScreenState.Loading -> SearchLoading()
            is SearchScreenState.Error -> SearchError(uiState.error)
            is SearchScreenState.Content -> SearchContent(
                uiState.content,
                uiState.found,
                onVacancyClick = onVacancyClick
            )

            is SearchScreenState.LoadingNextPage -> SearchLoading()
            is SearchScreenState.LoadingNextPageError -> LoadingPageError(uiState.error)
        }
    }

}

@Composable
fun LoadingPageError(error: ErrorType) {

}

@Composable
fun SearchContent(vacancies: List<Vacancy>, foundNumber: Int, onVacancyClick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .height(28.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = YpBlue)
        ) {
            Text(
                text = pluralStringResource(id = R.plurals.search_result_message, count = foundNumber, foundNumber),
                fontFamily = YsDispalyFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }

        VacancyList(vacancies = vacancies, onVacancyClick = onVacancyClick)
    }
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
