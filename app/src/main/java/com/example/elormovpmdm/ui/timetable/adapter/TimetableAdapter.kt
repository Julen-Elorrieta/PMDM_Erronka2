package com.example.elormovpmdm.ui.timetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.R
import com.example.elormovpmdm.domain.model.UserResponse

class TimetableAdapter(
    private var userList: List<UserResponse> = emptyList(),
    private val onItemSelected: (UserResponse) -> Unit
): RecyclerView.Adapter<TimetableViewHolder>() {
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimetableViewHolder {
        return TimetableViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_card, parent, false)
        )
    }

    fun updateList(listUpdated: List<UserResponse>) {
        userList = listUpdated
        notifyDataSetChanged()
    }
    
    override fun onBindViewHolder(
        holder: TimetableViewHolder,
        position: Int
    ) {
        holder.render(userList[position], onItemSelected)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}