package com.ikemura.android_kotlin_lab.main

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.ikemura.android_kotlin_lab.R
import com.ikemura.android_kotlin_lab.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // FAB -> Card
        binding.fab.setOnClickListener { fab ->
            val transform = MaterialContainerTransform(requireContext()).apply {
                startView = fab
                endView = binding.card
                pathMotion = MaterialArcMotion()
                duration = 500
                scrimColor = Color.TRANSPARENT
            }
            TransitionManager.beginDelayedTransition(binding.container, transform)
            fab.visibility = View.GONE
            binding.card.visibility = View.VISIBLE
        }

        // Card -> Fab
        binding.card.setOnClickListener { card ->
            val transform = MaterialContainerTransform(requireContext()).apply {
                startView = card
                endView = binding.fab
                pathMotion = MaterialArcMotion()
                duration = 500
                scrimColor = Color.TRANSPARENT
            }
            TransitionManager.beginDelayedTransition(binding.container, transform)
            binding.fab.visibility = View.VISIBLE
            card.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
