package com.example.elderly.Screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elderly.Models.Viaje
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {

    private var db: FirebaseFirestore = Firebase.firestore

    private val _viajes = MutableStateFlow<List<Viaje>>(emptyList())
    val viaje: StateFlow<List<Viaje>> = _viajes


    init {

        getArtists()
        println(getArtists())
    }
    private fun getArtists() {
        viewModelScope.launch {
            val result: List<Viaje> = withContext(Dispatchers.IO) {
                getAllArtists()
            }
            _viajes.value = result
        }
    }

    private suspend fun getAllArtists(): List<Viaje> {
        return try {
            db.collection("viajes").get().await().documents.mapNotNull { snapshot ->
                snapshot.toObject(Viaje::class.java)
            }
        } catch (e: Exception) {
            Log.i("Julian", e.toString())
            emptyList()
        }
    }
}