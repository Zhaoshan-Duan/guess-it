package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L

    }

    private val timer: CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>() // use internally

    // using backing property
    val score: LiveData<Int> // use for UI Controllers
        get() = _score

    // The current time
    private var _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    // Represent game finish event
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        _word.value = ""
        _score.value = 0
        _eventGameFinish.value = false

        resetList()
        nextWord()

        // Creates a timer which triggers the end of the game when it finishes
        // CountdownTimer(Total time, time of each tick)
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                // Happen each tick
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                // When the timer finishes
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()


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
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            // Game finishes
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }

    /** Methods for buttons presses **/

    fun onSkip() {
        // null check
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        // null check
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    /**
     * Represent that the event has been handled
     */
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    /**
     * onCleared
     */
    override fun onCleared() {
        super.onCleared()
        // cancel the timer to prevent memeroy leaks
        timer.cancel()
    }
}