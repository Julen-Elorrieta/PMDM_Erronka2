package com.example.elormovpmdm.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.elormovpmdm.databinding.FragmentTeachersSchedulesBinding
import com.example.elormovpmdm.domain.model.User
import com.example.elormovpmdm.ui.schedule.adapter.SchedulesAdapter
import com.example.elormovpmdm.ui.schedule.userSchedule.UserScheduleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class TeachersSchedulesFragment : Fragment() {

    private var _binding: FragmentTeachersSchedulesBinding? = null
    private val binding get() = _binding!!
    private val teacherScheduleViewModel: TeachersSchedulesViewModel by viewModels()
    private var users: List<User> = emptyList()
    private lateinit var schedulesAdapter: SchedulesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeachersSchedulesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }

    private fun initComponents() {
        schedulesAdapter = SchedulesAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvSchedules.layoutManager = GridLayoutManager(context, 1)
        binding.rvSchedules.adapter = schedulesAdapter
    }
    
    private fun onItemSelected(user: User) {
        val intent = Intent(requireActivity(), UserScheduleActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivity(intent)
    }
    
    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                teacherScheduleViewModel.users.collect { users ->
                    if (users.isNotEmpty()) {
                        Log.i("GVA", "Profesores encontrados: ${users.size}")
                    }
                    schedulesAdapter.updateList(users)
                }
            }
        }
    }
}