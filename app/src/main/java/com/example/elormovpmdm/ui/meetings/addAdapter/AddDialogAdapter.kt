package com.example.elormovpmdm.ui.meetings.addAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.R
import com.example.elormovpmdm.domain.model.User

class AddDialogAdapter(
    private var userList: List<User> = emptyList(),
    private val onMeetingUserSelected: (User) -> Unit
): RecyclerView.Adapter<AddDialogViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddDialogViewHolder {
        return AddDialogViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_card, parent, false)
        )
    }
    
    fun updateList(listUpdated: List<User>) {
        userList = listUpdated
        notifyDataSetChanged()
    }
    
    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onBindViewHolder(
        holder: AddDialogViewHolder,
        position: Int
    ) {
        holder.render(
            userList[position],
            isSelected = position == selectedPosition,
            onMeetingUserSelected = { user ->
                val currentPosition = holder.adapterPosition
                if (currentPosition == RecyclerView.NO_POSITION) return@render

                val previousPosition = selectedPosition
                selectedPosition = currentPosition

                if (previousPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(previousPosition)
                }
                notifyItemChanged(currentPosition)

                onMeetingUserSelected(user)
            }
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}