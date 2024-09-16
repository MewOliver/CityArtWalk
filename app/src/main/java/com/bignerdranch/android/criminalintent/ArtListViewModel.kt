package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ArtListViewModel"

class ArtListViewModel : ViewModel() {

    private val artRepository = ArtRepository.get()

    private val _arts: MutableStateFlow<List<Art>> = MutableStateFlow(emptyList())
    val arts: StateFlow<List<Art>>
        get() = _arts.asStateFlow()

    init {
        viewModelScope.launch {
            artRepository.getArts().collect {
                _arts.value = it
            }
        }
    }
    suspend fun addArt(art: Art) {
        artRepository.addArt(art)
    }
}