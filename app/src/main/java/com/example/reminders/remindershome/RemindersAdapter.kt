package com.example.reminders.remindershome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reminders.database.Reminder
import com.example.reminders.databinding.ReminderListItemBinding

class RemindersAdapter : ListAdapter<Reminder, RecyclerView.ViewHolder>(ReminderDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ReminderListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val reminder = getItem(position)
                holder.bind(reminder)
            }
        }
    }

    class ViewHolder(private val binding: ReminderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.reminderTitle.text = reminder.title
            binding.reminderDescription.text = reminder.description
            binding.clickListener = ReminderListItemListener {
                itemView.findNavController().navigate(
                    RemindersHomeFragmentDirections.actionRemindersHomeFragmentToReminderDetailFragment(
                        reminder
                    )
                )
            }
            binding.executePendingBindings()
        }
    }
}

class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.title == newItem.title && oldItem.description == oldItem.description
    }
}

class ReminderListItemListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
}
