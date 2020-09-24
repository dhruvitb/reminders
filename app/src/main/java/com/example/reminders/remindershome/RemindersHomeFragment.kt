package com.example.reminders.remindershome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.reminders.database.AppDatabase
import com.example.reminders.databinding.FragmentRemindersHomeBinding

class RemindersHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRemindersHomeBinding
            .inflate(inflater, container, false)
        val database = AppDatabase.getInstance(requireActivity().applicationContext)
        val viewModelFactory = RemindersHomeViewModelFactory(database)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RemindersHomeViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.addSuccess.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(activity, "added a new reminder", Toast.LENGTH_SHORT).show()
                viewModel.finishAdding()
            }
        })

        viewModel.allReminders.observe(viewLifecycleOwner, {
            // TODO do stuff with the actual list of reminders
        })

        return binding.root
    }
}