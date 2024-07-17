package org.jojo.guess_it.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.color.utilities.Score
import org.jojo.guess_it.R
import org.jojo.guess_it.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )

        viewModelFactory = ScoreViewModelFactory(
            ScoreFragmentArgs.fromBundle(requireArguments()).score)
        // tell view model provider to use the factory
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]

        binding.scoreViewModel = viewModel

        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }

        viewModel.eventPlayAgain.observe(viewLifecycleOwner) { playAgain ->
           if (playAgain) {
               findNavController().navigate(ScoreFragmentDirections.actionRestart())
               viewModel.onPlayAgainComplete()
           }
        }
        return binding.root
    }
}