package com.example.reminders.reminderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.reminders.databinding.FragmentReminderDetailBinding

class ReminderDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReminderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}