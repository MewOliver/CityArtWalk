package com.bignerdranch.android.cityartwalk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ArtDetailViewModel(artId: UUID) : ViewModel() {
    private val artRepository = ArtRepository.get()

    private val _art: MutableStateFlow<Art?> = MutableStateFlow(null)
    val art: StateFlow<Art?> = _art.asStateFlow()

    init {
        viewModelScope.launch {
            _art.value = artRepository.getArt(artId)
        }
    }

    fun updateArt(onUpdate: (Art) -> Art) {
        _art.update { oldArt ->
            oldArt?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        art.value?.let { artRepository.updateArt(it) }
    }

}

class ArtDetailViewModelFactory (
    private val artId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return ArtDetailViewModel(artId) as T
    }
}