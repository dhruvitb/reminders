package com.example.reminders.reminderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reminders.database.AppDatabase
import com.example.reminders.database.Reminder
import com.example.reminders.databinding.FragmentReminderDetailBinding
import com.example.reminders.hideKeyboardFrom
import com.example.reminders.makeNotification

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

        if (reminderId == 0) {
            binding.deleteReminder.visibility = View.GONE
        }

        viewModel.saveReminderClicked.observe(viewLifecycleOwner, {
            if (it) {
                val reminder = Reminder(
                    reminderId,
                    binding.reminderDetailTitle.text.toString(),
                    binding.reminderDetailDescription.text.toString()
                )
                viewModel.saveReminder(reminder)
            }
        })

        viewModel.savedReminder.observe(viewLifecycleOwner, {
            if (it != null) {
                makeNotification(requireContext(), it)
                viewModel.finishSave()
            }
        })

        viewModel.removeNotification.observe(viewLifecycleOwner, {
            if (it != null) {
                NotificationManagerCompat.from(requireContext()).cancel(it)
            }
        })

        viewModel.navigateHome.observe(viewLifecycleOwner, {
            if (it) {
                hideKeyboardFrom(requireContext(), requireView())
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
}
