package com.coin.coindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.coin.coindemo.adapters.CoinsAdapter
import com.coin.coindemo.data.CoinState
import com.coin.coindemo.databinding.ActivityMainBinding
import com.coin.coindemo.viewmodels.CoinsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CoinsViewModel by viewModels()
    private val adapter = CoinsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.filteredList.observe(this) {coins ->
            adapter.addData(coins)
        }
        binding.searchEditText.doAfterTextChanged {
            viewModel.updateSearchQuery(it.toString())
        }
        binding.allFilterButton.setOnClickListener {
            binding.allFilterButton.isSelected = !binding.allFilterButton.isSelected
            toggleFilter(CoinState.ALL.value)
        }
        binding.activeFilterButton.setOnClickListener {
            toggleFilter(CoinState.ACTIVE.value)
        }
        binding.filterTypeButton.setOnClickListener {
            toggleFilter(CoinState.TYPE_COIN.value)
        }
        binding.newFilterButton.setOnClickListener {
            toggleFilter(CoinState.NEW.value)
        }
        binding.coinsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.coinsRecyclerView.adapter = adapter
    }

    private fun toggleFilter(filter: String) {
        viewModel.filters.value = filter
    }
}