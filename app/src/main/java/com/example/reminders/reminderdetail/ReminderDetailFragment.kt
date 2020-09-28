package com.example.reminders.reminderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reminders.database.AppDatabase
import com.example.reminders.databinding.FragmentReminderDetailBinding

class ReminderDetailFragment : Fragment() {
    private lateinit var binding: FragmentReminderDetailBinding
    private var reminderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: ReminderDetailFragmentArgs by navArgs()
        reminderId = args.reminderId
        binding = FragmentReminderDetailBinding.inflate(inflater, container, false)
        val database = AppDatabase.getInstance(requireActivity().applicationContext)
        val viewModelFactory = RemindersDetailViewModelFactory(
            database,
            reminderId
        )
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(ReminderDetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.saveReminderClicked.observe(viewLifecycleOwner, {
            if (it) saveReminder()
        })

        viewModel.navigateHome.observe(viewLifecycleOwner, {
            if (it) {
                this.findNavController()
                    .navigate(
                        ReminderDetailFragmentDirections
                            .actionReminderDetailFragmentToRemindersHomeFragment()
                    )
                viewModel.finishNavigating()
            }
        })

        return binding.root
    }

    private fun saveReminder() {
        if (binding.reminderDetailTitle.toString().isNotEmpty()) {
            binding.viewModel?.saveReminder(
                reminderId,
                binding.reminderDetailTitle.text.toString(),
                binding.reminderDetailDescription.text.toString()
            )
        }
    }
}
