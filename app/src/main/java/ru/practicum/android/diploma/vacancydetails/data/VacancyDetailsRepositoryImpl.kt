package ru.practicum.android.diploma.vacancydetails.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.data.dto.requests.VacancyDetailsSearchRequest
import ru.practicum.android.diploma.core.data.dto.responses.VacancyDetailsSearchResponse
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient.Companion.RC_NO_INTERNET
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient.Companion.RC_OK
import ru.practicum.android.diploma.core.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancydetails.domain.api.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacancyDetailsRepository {

    override fun getVacancyDetailsById(vacancyId: String): Flow<Resource<VacancyDetails>> = flow {
        val response = networkClient.doRequest(VacancyDetailsSearchRequest(vacancyId))

        when (response.resultCode) {
            RC_NO_INTERNET -> emit(Resource.Error("No internet"))

            RC_OK -> emit(
                Resource.Success(VacancyDetailsDtoMapper.map((response as VacancyDetailsSearchResponse).dto))
            )

            else -> emit(Resource.Error("Server error"))
        }
    }.flowOn(Dispatchers.IO)
}