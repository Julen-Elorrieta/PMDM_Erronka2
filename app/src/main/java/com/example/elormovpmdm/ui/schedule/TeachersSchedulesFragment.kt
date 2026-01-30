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
import com.example.elormovpmdm.R
import com.example.elormovpmdm.databinding.FragmentTeachersSchedulesBinding
import com.example.elormovpmdm.domain.model.User
import com.example.elormovpmdm.ui.schedule.adapter.SchedulesAdapter
import com.example.elormovpmdm.ui.schedule.userSchedule.UserScheduleActivity
import kotlinx.coroutines.launch
import kotlin.getValue

class TeachersSchedulesFragment : Fragment() {

    private var _binding: FragmentTeachersSchedulesBinding? = null
    private val binding get() = _binding!!
    private val teacherScheduleViewModel: TeachersSchedulesViewModel by viewModels()
    private lateinit var schedulesAdapter: SchedulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teachers_schedules, container, false)
    }

    private fun initComponents() {
        schedulesAdapter = SchedulesAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvTimetable.layoutManager = GridLayoutManager(context, 1)
        binding.rvTimetable.adapter = schedulesAdapter
    }


    private fun onItemSelected(user: User) {
        val intent = Intent(requireActivity(), UserScheduleActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivity(intent)
    }


    private fun initUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                teacherScheduleViewModel.users.collect {
                    schedulesAdapter.updateList(it)
                }
            }
        }
    }

}