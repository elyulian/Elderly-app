package com.example.elderly.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elderly.Models.Viaje
import com.example.elderly.R
import com.example.elderly.ui.theme.Black
import com.example.elderly.ui.theme.Yellow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(db: FirebaseFirestore, navigateViajes:()->Unit={},navigatePqrs:()->Unit={}, viewModel: HomeViewModel = HomeViewModel()) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    //  var clientes by remember { mutableStateOf(listOf<Cliente>()) }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current


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
                IconButton(onClick = { /*navController.navigate("registro")*/ }) {

                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))

                IconButton(onClick = { navigatePqrs() }) {
                    Icon(
                        Icons.Filled.Build,
                        contentDescription = "Servicio"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navigateViajes() }) {
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

        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Yellow
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
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
                Image(painter = painterResource(id = R.drawable.excursion), contentDescription = "")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "¡Registra tu Viaje!",
                    textAlign = TextAlign.Start,
                    fontSize = 35.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(15.dp))

                //registro
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Viaje") }
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripcion") }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        val viaje = Viaje(
                            nombre = nombre,
                            descripcion = descripcion,
                            image = "https://www.shutterstock.com/image-photo/travel-world-monument-concept-260nw-2521518873.jpg"
                        )
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                db.collection("viajes").add(viaje)
                            }
                            Toast.makeText(context, "Viaje Registrado", Toast.LENGTH_SHORT).show()
                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow)
                ) {
                    Text(
                        "Registrar",
                        fontSize = 20.sp,
                        color = Black
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

            }


        }
    }

}

