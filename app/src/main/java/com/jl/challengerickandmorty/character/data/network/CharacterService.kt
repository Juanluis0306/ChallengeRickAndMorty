package com.jl.challengerickandmorty.character.data.network

import com.jl.challengerickandmorty.character.data.network.response.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterService @Inject constructor(private val characterClient: CharacterClient) {

    suspend fun getCharacter(): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            val response =
                characterClient.getCharacter()
            response.body()
        }
    }
}