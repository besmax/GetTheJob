package ru.practicum.android.diploma.core.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.YsDispalyFamily
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.util.getSalaryDescription

@Composable
fun VacancyList(
    vacancies: List<Vacancy>,
    onVacancyClick: (String) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
    ) {
        items(
            items = vacancies,
            key = { vacancy -> vacancy.id }
        ) { vacancy ->
            VacancyListItem(vacancy = vacancy, onVacancyClick = onVacancyClick)
        }
    }

}

@Composable
fun VacancyListItem(
    vacancy: Vacancy,
    onVacancyClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .clickable { onVacancyClick.invoke(vacancy.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = vacancy.logo,
            contentDescription = "Company logo",
            placeholder = painterResource(id = R.drawable.ic_vacancy_placeholder),
            error = painterResource(id = R.drawable.ic_vacancy_placeholder),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column() {
            Text(
                text = vacancy.name,
                fontFamily = YsDispalyFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Text(
                text = vacancy.employerName,
                fontFamily = YsDispalyFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Text(
                text = getSalaryDescription(
                    LocalContext.current.resources,
                    vacancy.salaryFrom,
                    vacancy.salaryTo,
                    vacancy.salaryCurrency
                ),
                fontFamily = YsDispalyFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    }
}
