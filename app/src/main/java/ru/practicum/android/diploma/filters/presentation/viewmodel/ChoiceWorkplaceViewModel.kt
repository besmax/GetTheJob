package ru.practicum.android.diploma.filters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.api.GetDataRepo
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.filters.presentation.state.ChoiceWorkplaceScreenState
import javax.inject.Inject

@HiltViewModel
class ChoiceWorkplaceViewModel @Inject constructor(
    private val getFiltersRepository: GetDataRepo<Filters>,
) : ViewModel() {

    private val _screenState = MutableLiveData<ChoiceWorkplaceScreenState>()
    val screenState: LiveData<ChoiceWorkplaceScreenState> = _screenState

    init {
        getFilters()
    }

    fun getFilters() {
        viewModelScope.launch {
            getFiltersRepository.get().collect() { currentFilters ->
                if (currentFilters != null) {
                    var countryName: String? = currentFilters.countryName
                    val isBtnSelectVisible = countryName != null || currentFilters.regionName != null
                    _screenState.postValue(
                        ChoiceWorkplaceScreenState(
                            country = countryName,
                            region = currentFilters.regionName,
                            isBtnSelectVisible = isBtnSelectVisible
                        )
                    )
                }
            }
        }
    }
}