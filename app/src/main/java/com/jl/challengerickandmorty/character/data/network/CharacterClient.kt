package com.jl.challengerickandmorty.character.data.network

import com.jl.challengerickandmorty.character.data.network.response.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface CharacterClient {

    @GET("character/?page=1")
    suspend fun getCharacter(): Response<CharacterResponse>
}