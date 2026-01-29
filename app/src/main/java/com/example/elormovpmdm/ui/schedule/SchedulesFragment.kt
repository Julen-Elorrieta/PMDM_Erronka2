package com.example.elormovpmdm.ui.schedule

import android.content.Intent
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
import com.example.elormovpmdm.databinding.FragmentSchedulesBinding
import com.example.elormovpmdm.domain.SessionManager
import com.example.elormovpmdm.domain.model.UserResponse
import com.example.elormovpmdm.ui.schedule.adapter.SchedulesAdapter
import com.example.elormovpmdm.ui.schedule.userSchedule.UserScheduleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchedulesFragment : Fragment() {

    private var _binding: FragmentSchedulesBinding? = null
    private val binding get() = _binding!!
    private val schedulesViewModel: SchedulesViewModel by viewModels()
    private lateinit var schedulesAdapter: SchedulesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchedulesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }

    private fun initComponents() {
        schedulesAdapter = SchedulesAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvTimetable.layoutManager = GridLayoutManager(context, 1)
        binding.rvTimetable.adapter = schedulesAdapter
        binding.currentUserCard.setOnClickListener {
            val intent = Intent(requireActivity(), UserScheduleActivity::class.java)
            intent.putExtra("user_id", SessionManager.currentUser?.id)
            startActivity(intent)
        }
    }

    private fun onItemSelected(user: UserResponse) {
        val intent = Intent(requireActivity(), UserScheduleActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivity(intent)
    }

    private fun initUI() {
        lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                schedulesViewModel.users.collect { 
                    schedulesAdapter.updateList(it)
                }
            }
        }
    }
}