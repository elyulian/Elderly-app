package com.example.elderly.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.elderly.Screens.HomeApp
import com.example.elderly.Screens.Login
import com.example.elderly.Screens.Pqrs
import com.example.elderly.Screens.Principal
import com.example.elderly.Screens.Registro
import com.example.elderly.Screens.Viajes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NavigationControl(
    navHostController: NavHostController,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {

    NavHost(navController = navHostController, startDestination = "principal") {
        composable("principal") {
            Principal(
                navigateToLogin = { navHostController.navigate("login") },
                navigateToRegistro = { navHostController.navigate("registro") }
            )
        }
        composable("login") {
            Login(auth,
                navigateAtras = { navHostController.navigate("principal") },
                navigateToHome = {navHostController.navigate("home")})
        }
        composable("registro") {
            Registro(auth,
                navigateAtras = { navHostController.navigate("principal") },
                navigateToHome = {navHostController.navigate("home")})
        }
        composable("home"){
            HomeApp(db,
                navigateViajes ={navHostController.navigate("viajes")},
                navigatePqrs = {navHostController.navigate("pqrs")})
        }
        composable("viajes"){
            Viajes(db,
                navigatePqrs = {navHostController.navigate("pqrs")},
                navigateToHome = {navHostController.navigate("home")})
        }
        composable("pqrs"){
            Pqrs(db,
                navigateViajes ={navHostController.navigate("viajes")},
                navigateToHome = {navHostController.navigate("home")} )
        }
    }


}