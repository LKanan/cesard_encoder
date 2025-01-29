package com.example.cesarencoder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cesarencoder.ui.theme.CesarEncoderTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CesarEncoderTheme{
                cesarEncoderApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun cesarEncoderAppPreview(){
//    CesarEncoderTheme(darkTheme = false) {
    CesarEncoderTheme(darkTheme = true) {
        cesarEncoderApp()
    }
}

@Composable
fun cesarEncoderApp(){
    Surface( // Surface prend automatiquement la couleur du thème
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Adapte le fond selon le mode clair/sombre
    ) {
        var input_text by remember { mutableStateOf("") }
        var encrypted_text = cesarEncoder(input_text.toString())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Prend toute la largeur disponible
                    .wrapContentHeight() // Hauteur ajustée au contenu
                    .border(0.dp, Color.Gray, RoundedCornerShape(10.dp)) // Contour blanc arrondi
                    .background(Color.Gray, RoundedCornerShape(10.dp)) // Fond gris
//                    .padding(16.dp) // Marge interne pour le contenu
            ) {
            clearMessageInputField(value = input_text, onValueChange = {input_text=it})
            }
            Spacer(modifier= Modifier.height(40.dp))
            Text(text="Message crypté")
            Spacer(modifier= Modifier.height(10.dp))
            encryptedMessageText(encrypted_text)
//            RoundedBox()
//            Text("Bonjour Jetpack Compose!", color = MaterialTheme.colorScheme.onBackground)
        }
    }

}
@Composable
fun clearMessageInputField(value:String, onValueChange:(String)-> Unit,){

    // Récupère la couleur de fond actuelle du thème
    val backgroundColor = MaterialTheme.colorScheme.background
//    val textColor = if (backgroundColor.luminance() > 0.5) Color.Black else Color.White // Adapte la couleur du texte
    val textColor = Color.White // Adapte la couleur du texte

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text("Saisissez le texte à encoder") },
        textStyle = LocalTextStyle.current.copy(color = textColor), // Adapte la couleur du texte
        colors = TextFieldDefaults.colors(
            disabledIndicatorColor = Color.Transparent, // Couleur quand désactivé
            focusedContainerColor = Color.Gray, // Fond du champ actif
            unfocusedContainerColor = Color.Gray // Fond du champ inactif
        )
    )
}

@Composable
fun encryptedMessageText(text: String){

    Box(
        modifier = Modifier
            .fillMaxWidth() // Prend toute la largeur disponible
            .wrapContentHeight() // Hauteur ajustée au contenu
            .border(0.dp, Color.Gray, RoundedCornerShape(10.dp)) // Contour blanc arrondi
            .background(Color.Gray, RoundedCornerShape(10.dp)) // Fond gris
//                    .padding(16.dp) // Marge interne pour le contenu
    ) {
        Text(text=text,
        modifier = Modifier
            .padding(5.dp),
            color = Color.White)
    }
}

fun cesarEncoder(text: String, shift: Int = 3): String {
    return text.map { char ->
        when {
            char.isUpperCase() -> {
                val shiftedChar = (char - 'A' + shift) % 26 + 'A'.toInt()
                shiftedChar.toChar()
            }
            char.isLowerCase() -> {
                val shiftedChar = (char - 'a' + shift) % 26 + 'a'.toInt()
                shiftedChar.toChar()
            }
            else -> char
        }
    }.joinToString("")
}

