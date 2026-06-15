package com.sanzh.converterapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanzh.currency_library.domain.interactor.CurrencyInteractor
import com.sanzh.converterapp.presentation.event.CurrencyEvent
import com.sanzh.converterapp.presentation.mapper.CurrencyUiMapper
import com.sanzh.converterapp.presentation.state.CurrencyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val interactor: CurrencyInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyUiState())
    val uiState: StateFlow<CurrencyUiState> = _uiState.asStateFlow()

    // Cached domain currencies for conversion
    private var domainCurrencies = listOf<com.sanzh.currency_library.domain.model.Currency>()

    init {
        onEvent(CurrencyEvent.LoadCurrencies("USD"))
    }

    // UDF: single entry point for all user events
    fun onEvent(event: CurrencyEvent) {
        when (event) {
            is CurrencyEvent.LoadCurrencies -> loadCurrencies(event.base)
            is CurrencyEvent.ConvertMoney -> convertMoney(event.amount, event.fromCode, event.toCode)
            is CurrencyEvent.ClearConversion -> _uiState.update { it.copy(convertedAmount = null) }
        }
    }

    private fun loadCurrencies(base: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                domainCurrencies = interactor.loadCurrencies(base)
                val uiModels = CurrencyUiMapper.toUiModelList(domainCurrencies)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currencies = uiModels,
                        baseCurrency = base
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Unknown error")
                }
            }
        }
    }

    private fun convertMoney(amount: Double, fromCode: String, toCode: String) {
        val from = domainCurrencies.find { it.code == fromCode } ?: return
        val to = domainCurrencies.find { it.code == toCode } ?: return
        val result = interactor.convert(amount, from, to)
        _uiState.update {
            it.copy(convertedAmount = "$amount $fromCode = ${String.format(Locale.US, "%.4f", result)} $toCode")
        }
    }
}