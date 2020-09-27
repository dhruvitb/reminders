package com.example.reminders.remindershome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        val viewModel: RemindersHomeViewModel by viewModels {
            RemindersHomeViewModelFactory(database)
        }
        binding.viewModel = viewModel

        val remindersAdapter = RemindersAdapter()
        binding.remindersRecyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = remindersAdapter
        }

        viewModel.navigateToNewReminder.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(
                        RemindersHomeFragmentDirections
                            .actionRemindersHomeFragmentToReminderDetailFragment(0)
                    )
                viewModel.finishNewReminderNavigation()
            }
        })

        viewModel.allReminders.observe(viewLifecycleOwner, {
            remindersAdapter.submitList(it)
        })

        return binding.root
    }
}