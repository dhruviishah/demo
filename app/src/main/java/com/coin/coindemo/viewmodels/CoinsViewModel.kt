package com.coin.coindemo.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coin.coindemo.data.CoinState
import com.coin.coindemo.data.GetCoinResponse
import com.coin.coindemo.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    private val _coins = MutableLiveData<List<GetCoinResponse>>()

    private val _filteredList = MediatorLiveData<List<GetCoinResponse>>()
    val filteredList: LiveData<List<GetCoinResponse>> get() = _filteredList

    private val _searchQuery = MutableLiveData<String>()
    private val searchQuery: LiveData<String> get() = _searchQuery

    var filters = MutableLiveData<String>()

    init {
        fetchCoins()
        _filteredList.addSource(_searchQuery) { query ->
            applyFiltersAndSearch()
        }
        _filteredList.addSource(filters) {
            applyFiltersAndSearch()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun fetchCoins() {
        viewModelScope.launch {
                val coins = withContext(Dispatchers.IO) {
                    repository.fetchCoins()
                }
                _coins.postValue(coins.body())
                _filteredList.postValue(coins.body())
        }
    }

    private fun applyFiltersAndSearch() {
        val allCoins = _coins.value.orEmpty()
        val filtered = allCoins.filter { coin ->
            val searchText = searchQuery.value.orEmpty()
            val matchesSearch = searchText.isEmpty() || coin.name.contains(searchText, true) || coin.symbol.contains(searchText, true)

            val activeFilter = filters.value?.equals(CoinState.ACTIVE.value) == true && coin.isActive
            val typeFilter = filters.value?.equals(CoinState.TYPE_COIN.value) == true && coin.type == CoinState.TYPE_COIN.value
            val newFilter = filters.value?.equals(CoinState.NEW.value) == true && coin.isNew
            val isAllFilter = filters.value?.equals(CoinState.ALL.value) == true

            matchesSearch && (activeFilter || typeFilter || newFilter || isAllFilter)
        }

        _filteredList.value = filtered
    }

}