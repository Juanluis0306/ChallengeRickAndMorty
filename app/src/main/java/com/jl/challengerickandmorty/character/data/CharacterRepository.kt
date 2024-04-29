package com.jl.challengerickandmorty.character.data

import com.jl.challengerickandmorty.character.data.network.CharacterService
import com.jl.challengerickandmorty.character.data.network.response.CharacterResponse
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val api: CharacterService) {

    suspend fun getCharacter(): CharacterResponse? {
        return api.getCharacter()
    }
}