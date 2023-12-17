package ru.practicum.android.diploma.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.api.GetDataRepo
import ru.practicum.android.diploma.core.domain.api.SaveDataRepo
import ru.practicum.android.diploma.core.domain.models.ErrorType
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.filters.domain.model.Industry
import ru.practicum.android.diploma.filters.presentation.state.IndustryScreenState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce
import javax.inject.Inject

@HiltViewModel
class ChoiceIndustryViewModel @Inject constructor(
    private val industryRepository: GetDataRepo<Resource<List<Industry>>>,
    private val getFiltersRepository: GetDataRepo<Filters>,
    private val saveFiltersRepository: SaveDataRepo<Filters>,
) : ViewModel() {

    private val originalList = mutableListOf<Industry>()
    private var lastSelectedIndustry: Industry? = null
    private var lastSelectedIndex: Int = -1

    private val searchDebounce: (String) -> Unit =
        debounce(SEARCH_DELAY_IN_MILLIS, viewModelScope, true) { searchText ->
            searchRequest(searchText)
        }
    private var lastSearchedText: String? = null

    private val _state = MutableLiveData<IndustryScreenState>()
    val state: LiveData<IndustryScreenState>
        get() = _state

    fun getIndustries() {
        if (originalList.isNotEmpty()) {
            _state.postValue(
                IndustryScreenState.Content(
                    industries = originalList,
                    applyBtnVisible = lastSelectedIndustry != null
                )
            )
            return
        }
        viewModelScope.launch {
            _state.postValue(IndustryScreenState.Loading)
            industryRepository.get().collect {
                when (it) {
                    is Resource.Error -> {
                        _state.postValue(IndustryScreenState.Error(it.errorType!!))
                    }

                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            _state.postValue(IndustryScreenState.Error(ErrorType.NO_CONTENT))
                        } else {
                            originalList.addAll(it.data)
                            _state.postValue(
                                IndustryScreenState.Content(
                                    industries = originalList,
                                    applyBtnVisible = lastSelectedIndustry != null
                                )
                            )
                        }
                    }

                    null -> {}
                }
            }
        }
    }


    fun search(searchText: String) {
        if (searchText != lastSearchedText) {
            searchDebounce(searchText)
            lastSearchedText = searchText
        }
    }

    private fun searchRequest(searchText: String) {
        if (searchText.isEmpty()) {
            getIndustries()
            return
        }
        _state.postValue(IndustryScreenState.Loading)
        val filteredList = mutableListOf<Industry>()
        originalList.forEach { industry ->
            if (industry.name.contains(searchText, true)) {
                filteredList.add(industry)
            }
        }
        if (filteredList.isEmpty()) {
            _state.postValue(IndustryScreenState.Error(ErrorType.NO_CONTENT))
        } else {
            _state.postValue(
                IndustryScreenState.Content(
                    industries = filteredList,
                    applyBtnVisible = lastSelectedIndustry != null
                )
            )
        }
    }

    fun updateIndustrySelected(industry: Industry, position: Int, currentList: List<Industry>? = null) {
        val newList = currentList?.toMutableList()
        val updatedIndustry = industry.copy(selected = !industry.selected)

        if (lastSelectedIndustry != null) {
            // Убираем выделение с последней отрасли
            originalList[lastSelectedIndex] = lastSelectedIndustry!!.copy(selected = false)
            val index = newList?.indexOf(lastSelectedIndustry)
            if (index != null && index != -1) {
                newList[index] = lastSelectedIndustry!!.copy(selected = false)
            }
        }
        if (industry != lastSelectedIndustry) {
            // Выделяем новую область, если она отличается от последней выбранной
            val index = if (newList != null) originalList.indexOf(industry) else position
            originalList[index] = updatedIndustry
            newList?.set(position, updatedIndustry)
            lastSelectedIndustry = updatedIndustry
            lastSelectedIndex = index

        } else {
            // Если кликнули на ту же, то просто обнуляем значения
            lastSelectedIndustry = null
            lastSelectedIndex = -1
        }

        _state.postValue(
            IndustryScreenState.Content(
                industries = newList ?: originalList,
                applyBtnVisible = lastSelectedIndustry != null
            )
        )
    }

    fun saveIndustry() {
        viewModelScope.launch {
            getFiltersRepository.get().collect() { currentFilters ->
                val updatedFilters = currentFilters?.copy(
                    industryId = lastSelectedIndustry?.id,
                    industryName = lastSelectedIndustry?.name
                )
                    ?: Filters(
                        regionId = null,
                        regionName = null,
                        countryId = null,
                        countryName = null,
                        salary = null,
                        salaryFlag = null,
                        industryId = lastSelectedIndustry?.id,
                        industryName = lastSelectedIndustry?.name,
                        currency = null
                    )
                saveFiltersRepository.save(updatedFilters)
            }
        }
    }

    companion object {
        private const val SEARCH_DELAY_IN_MILLIS = 2000L
    }
}
