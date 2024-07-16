package org.jojo.guess_it.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import org.jojo.guess_it.R
import org.jojo.guess_it.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.word.observe(viewLifecycleOwner) { newWord ->
            binding.wordText.text = newWord
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        }

        viewModel.currentTime.observe(viewLifecycleOwner) {newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        }

        return binding.root
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore()
        action.setScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }
}

