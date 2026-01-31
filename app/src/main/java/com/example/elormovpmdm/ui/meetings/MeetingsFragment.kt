package com.example.elormovpmdm.ui.meetings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.FragmentMeetingsBinding
import com.example.elormovpmdm.domain.model.Meeting
import com.example.elormovpmdm.ui.meetings.adapter.MeetingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeetingsFragment : Fragment() {
    
    private var _binding: FragmentMeetingsBinding? = null
    private val binding get() = _binding!!
    private val meetingsViewModel: MeetingsViewModel by viewModels()
    private lateinit var meetingsAdapter: MeetingsAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeetingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }
    
    private fun initComponents() {
        meetingsAdapter = MeetingsAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvMeetins.layoutManager = GridLayoutManager(context, 1)
        binding.rvMeetins.adapter = meetingsAdapter
    }
    
    private fun onItemSelected(meeting: Meeting) {
        
    }
    
    private fun initUI() {
        lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                meetingsViewModel.meetings.collect { 
                    meetingsAdapter.updateList(it)
                }
            }
        }
    }
}