package com.jl.challengerickandmorty.character.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jl.challengerickandmorty.character.data.network.response.Results


@Composable
fun CharacterScreen(characterViewModel: CharacterViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        characterViewModel.getCharacter()
        val isLoading by characterViewModel.isLoading.observeAsState(initial = false)
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

        } else {
            Body(Modifier.align(Alignment.Center), characterViewModel)
        }
    }
}


@Composable
fun Body(modifier: Modifier, characterViewModel: CharacterViewModel) {
    Column(modifier = modifier) {
        CharacterGridView(characterViewModel)
    }
}


@Composable
fun CharacterGridView(characterViewModel: CharacterViewModel) {
    val listCharacter: List<Results> by characterViewModel.listCharacterApi.observeAsState(
        listOf()
    )
    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(listCharacter) { character ->
            ItemCharacter(character = character, characterViewModel = characterViewModel)
        }
    })
}

@Composable
fun ItemCharacter(
    character: Results,
    characterViewModel: CharacterViewModel
) {
    val isDetailEnable by characterViewModel.isDetailEnable.observeAsState(initial = false)
    val itemSelect by characterViewModel.itemSelect.observeAsState()
    Card(
        border = BorderStroke(1.dp, Color.Gray), modifier = Modifier
            .width(200.dp)
            .padding(top = 4.dp, bottom = 4.dp, end = 4.dp, start = 4.dp),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GetImage(url = character.image ?: "", description = "Image")
            TextTitle(content = character.name ?: "")
            if (isDetailEnable && character == itemSelect) {
                RowDetail(key = "Status: ", character.status ?: "")
                RowDetail(key = "Species: ", character.species ?: "")
                RowDetail(key = "Type: ", character.type ?: "")
                RowDetail(key = "Gender: ", character.gender ?: "")
                RowDetail(key = "Origin: ", character.origin?.name ?: "")
                RowDetail(key = "Location: ", content = character.location?.name ?: "")
            }

            TextButton(
                onClick = {
                    characterViewModel.isDetailEnable.value = !isDetailEnable
                    characterViewModel.itemSelect.value = character
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = if (isDetailEnable && character == itemSelect) "Ocultar Detalle" else "Ver Detalle")
            }
        }

    }
}

@Composable
fun RowDetail(key: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Text(buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF4EA8E9)
                )
            ) {
                append(key)
            }
            append(content)
        })
    }
}

@Composable
fun TextTitle(content: String) {
    Text(
        text = content,
        maxLines = 1,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(4.dp)
    )
}

@Composable
fun GetImage(url: String, description: String) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = description,
        modifier = Modifier.size(170.dp),
        contentScale = ContentScale.Crop
    )
}