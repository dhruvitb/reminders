package com.example.reminders.remindershome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.reminders.databinding.FragmentRemindersHomeBinding

class RemindersHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRemindersHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}