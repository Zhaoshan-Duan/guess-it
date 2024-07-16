package org.jojo.guess_it.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.color.utilities.Score.score

class GameViewModel : ViewModel() {
    // The current word
    private val _word = object: MutableLiveData<String>() {
        override fun onActive() {
            super.onActive()
            Log.d("GameViewModel", "word LiveData is active")
        }
        override fun onInactive() {
            super.onInactive()
            Log.d("GameViewModel", "word LiveData is inactive")
        }
    }

    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = object: MutableLiveData<Int>() {
        override fun onActive() {
            super.onActive()
            Log.d("GameViewModel", "score LiveData is active")
        }

        override fun onInactive() {
            super.onInactive()
            Log.d("GameViewModel", "score LiveData is inactive")
        }
    }

    val score: LiveData<Int>
        get() = _score


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // a new live data that represent game finish EVENT
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init{
        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0
        _word.value = ""

    }
    override fun onCleared() {
        super.onCleared()
        Log.i("ViewViewModel", "GameViewModel destroyed!")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value?.minus( 1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus( 1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}