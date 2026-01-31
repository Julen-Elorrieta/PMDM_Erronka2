package com.example.elormovpmdm.ui.meetings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.elormovpmdm.R
import androidx.recyclerview.widget.RecyclerView
import com.example.elormovpmdm.domain.model.Meeting

class MeetingsAdapter(
    private var meetingList: List<Meeting> = emptyList(),
    private val onItemSelected: (Meeting) -> Unit
): RecyclerView.Adapter<MeetingsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeetingsViewHolder {
        return MeetingsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.meeting_card, parent, false)
        )
    }
    
    fun updateList(listUpdated: List<Meeting>) {
        meetingList = listUpdated
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: MeetingsViewHolder,
        position: Int
    ) {
        holder.render(meetingList[position], onItemSelected)
    }

    override fun getItemCount(): Int {
        return meetingList.size
    }
}