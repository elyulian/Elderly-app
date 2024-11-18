package com.example.elderly.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elderly.R
import com.example.elderly.ui.theme.Black
import com.example.elderly.ui.theme.SelectedField
import com.example.elderly.ui.theme.UnselectedField
import com.example.elderly.ui.theme.Yellow
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Registro(auth: FirebaseAuth, navigateAtras:() -> Unit={}, navigateToHome:()-> Unit ={}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.atras),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(24.dp).clickable { navigateAtras() }
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Text("Ingrese sus datos!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 27.sp, modifier = Modifier.padding(5.dp))
        Image(painter = painterResource(id = R.drawable.registro), contentDescription = "")
        Text("Email", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 27.sp, modifier = Modifier.padding(5.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(20.dp))
        Text("Contraseña", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 27.sp)
        TextField(
            value = password, onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        navigateToHome()
                        Log.i("Julian", "Si registra")
                    }else{
                        //Error
                        Log.i("Julian", "No registra")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow)
        ) {
            Text(text = "Registrarse", color = Black)
        }
    }
}
