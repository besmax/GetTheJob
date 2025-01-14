package ru.practicum.android.diploma.util

import android.content.res.Resources
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

fun getSalaryDescription(resources: Resources, salaryFrom: Int?, salaryTo: Int?, salaryCurrency: String?): String {
    return when {
        salaryFrom != null && salaryTo != null ->
            resources.getString(
                R.string.vacancy_salary_from_to,
                formatSalary(salaryFrom),
                formatSalary(salaryTo),
                salaryCurrency?.let { getCurrencySymbol(it) }
            )

        salaryFrom != null && salaryTo == null ->
            resources.getString(
                R.string.vacancy_salary_from,
                formatSalary(salaryFrom),
                salaryCurrency?.let { getCurrencySymbol(it) }
            )

        salaryFrom == null && salaryTo != null ->
            resources.getString(
                R.string.vacancy_salary_to,
                formatSalary(salaryTo),
                salaryCurrency?.let { getCurrencySymbol(it) }
            )

        else -> resources.getString(R.string.vacancy_salary_not_specified)
    }
}

private fun formatSalary(amount: Int): String {
    val format: NumberFormat = NumberFormat.getInstance(Locale.getDefault())
    return format.format(amount).replace(",", " ")
}
