package com.example.reminders.reminderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.reminders.database.AppDatabase
import com.example.reminders.databinding.FragmentReminderDetailBinding

class ReminderDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReminderDetailBinding.inflate(inflater, container, false)
        val database = AppDatabase.getInstance(requireActivity().applicationContext)
        val viewModelFactory = RemindersDetailViewModelFactory(
            database,
            navArgs<ReminderDetailFragmentArgs>().value.reminderId
        )
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ReminderDetailViewModel::class.java)
        binding.viewModel = viewModel

        return binding.root
    }
}
