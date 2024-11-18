package com.example.elderly.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.elderly.Models.Viaje
import com.example.elderly.R
import com.example.elderly.ui.theme.Black
import com.example.elderly.ui.theme.Yellow
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Viajes(db:FirebaseFirestore, navigatePqrs:()->Unit={},navigateToHome:()->Unit={}) {


    val scope = rememberCoroutineScope()

    val context = LocalContext.current
// Estado para almacenar la lista de usuarios
    val viajes = remember { mutableStateOf<List<Viaje>>(emptyList()) }

    // Obtener datos de Firestore
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("viajes")
            .get()
            .addOnSuccessListener { result ->
                // Convertir los documentos en instancias del data class Usuario
                val listaViajes = result.map { document ->
                    Viaje(
                        nombre = document.getString("nombre") ?: "Desconocido",
                        descripcion = document.getString("descripcion") ?: "No tiene",
                        image = document.getString("image") ?: "Sin imagen"
                    )
                }
                viajes.value = listaViajes
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error al obtener documentos", exception)
            }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                ),
                colors = topAppBarColors(
                    containerColor = Yellow,
                    titleContentColor = Black,
                ),
                title = {
                    Text(
                        text = "Elderly",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .clip(CutCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .height(60.dp),
                containerColor = Yellow
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navigateToHome() }) {

                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))

                IconButton(onClick = {navigatePqrs()  }) {
                    Icon(
                        Icons.Filled.Build,
                        contentDescription = "Servicio"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {  }) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "Movies"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*navController.navigate("peliculas")*/ }) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Movies"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            item {
                Image(painter = painterResource(id = R.drawable.tarea), contentDescription = "")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "¡Tus Viajes!",
                    textAlign = TextAlign.Start,
                    fontSize = 35.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(15.dp))

            }
            items(viajes.value){viajes ->
                ViajesCard(viajes)
            }

        }
    }


}

@Composable
fun ViajesCard(viaje: Viaje) {
    // Crear la tarjeta
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9C4) // Amarillo claro (Light Yellow)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Imagen del usuario
            Image(
                painter = rememberAsyncImagePainter(viaje.image),
                contentDescription = "Imagen de ${viaje.nombre}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape) // Imagen redonda
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre la imagen y el texto

            // Información del usuario
            Column {
                Text(text = "Nombre: ${viaje.nombre}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Descripcion: ${viaje.descripcion}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
