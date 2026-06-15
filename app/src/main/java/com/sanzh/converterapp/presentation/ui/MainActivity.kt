package com.sanzh.converterapp.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sanzh.converterapp.databinding.ActivityMainBinding
import com.sanzh.converterapp.presentation.event.CurrencyEvent
import com.sanzh.converterapp.presentation.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()
    private val adapter = CurrencyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCurrencies.adapter = adapter

        binding.btnRefresh.setOnClickListener {
            val base = binding.etBase.text.toString().uppercase().ifBlank { "USD" }
            viewModel.onEvent(CurrencyEvent.LoadCurrencies(base))
        }

        binding.btnConvert.setOnClickListener {
            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            val from = binding.etFrom.text.toString().uppercase()
            val to = binding.etTo.text.toString().uppercase()
            if (amount != null && from.isNotBlank() && to.isNotBlank()) {
                viewModel.onEvent(CurrencyEvent.ConvertMoney(amount, from, to))
            } else {
                Toast.makeText(this, "Fill all conversion fields", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.progressBar.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                adapter.submitList(state.currencies)

                state.error?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                }

                binding.tvConversionResult.text = state.convertedAmount ?: ""
                binding.tvConversionResult.visibility =
                    if (state.convertedAmount != null) View.VISIBLE else View.GONE
            }
        }
    }
}