package com.jl.challengerickandmorty.character.domain

import com.jl.challengerickandmorty.character.data.CharacterRepository
import com.jl.challengerickandmorty.character.data.network.response.CharacterResponse
import javax.inject.Inject

class CharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke(): CharacterResponse? {
        return repository.getCharacter()
    }
}