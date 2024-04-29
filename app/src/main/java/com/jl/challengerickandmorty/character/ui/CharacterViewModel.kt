package com.jl.challengerickandmorty.character.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jl.challengerickandmorty.character.data.network.response.Results
import com.jl.challengerickandmorty.character.domain.CharacterUseCase
import com.jl.challengerickandmorty.character.domain.model.CharacterList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val getCharacterUseCase: CharacterUseCase) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val isDetailEnable = MutableLiveData<Boolean>()
    val itemSelect = MutableLiveData<Results>()

    private val _listCharacterApi = MutableLiveData<List<Results>>()
    var listCharacterApi: LiveData<List<Results>> = _listCharacterApi


    fun getCharacter() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getCharacterUseCase()
            if (result != null) {
                _listCharacterApi.postValue(result.results)
                _isLoading.postValue(false)
            }
        }
    }

}