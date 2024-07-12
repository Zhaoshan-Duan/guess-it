package org.jojo.guess_it.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    init{
        Log.i("ViewViewModel", "GameViewModel created!")
    }
    override fun onCleared() {
        super.onCleared()
        Log.i("ViewViewModel", "GameViewModel destroyed!")
    }


}