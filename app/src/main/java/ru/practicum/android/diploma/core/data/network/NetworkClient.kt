package ru.practicum.android.diploma.core.data.network

import ru.practicum.android.diploma.core.data.dto.Response

interface NetworkClient {

    fun doRequest(request: Any): Response
}
