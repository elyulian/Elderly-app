package com.example.elderly.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
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
import com.example.elderly.Models.Recomendacion
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
fun Pqrs(db:FirebaseFirestore, navigateViajes:()->Unit={},navigateToHome:()->Unit={}) {

    var pqr by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }

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
                IconButton(onClick = { navigateToHome()}) {

                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))

                IconButton(onClick = { }) {
                    Icon(
                        Icons.Filled.Build,
                        contentDescription = "Servicio"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navigateViajes()}) {
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

                Spacer(modifier = Modifier.height(15.dp))

                Image(painter = painterResource(id = R.drawable.signo), contentDescription = "")
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "¡Ingrese su PQRS!",
                    textAlign = TextAlign.Start,
                    fontSize = 35.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Ingrese su nombre") }
                )
                //registro
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = pqr,
                    onValueChange = { pqr = it },
                    label = { Text("Ingrese su PQRS") }
                )
                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        val recomendacion = Recomendacion(
                            nombrePa = nombre,
                            Pqrs = pqr
                        )
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                db.collection("recomendaciones").add(recomendacion)
                            }
                            Toast.makeText(context, "PQRS Registrado", Toast.LENGTH_SHORT).show()
                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow)
                ) {
                    Text(
                        "Reclamo!",
                        fontSize = 20.sp,
                        color = Black
                    )
                }


            }
        }


    }
}