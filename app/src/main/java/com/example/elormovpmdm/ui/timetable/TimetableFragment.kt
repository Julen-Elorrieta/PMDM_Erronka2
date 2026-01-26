package com.example.elormovpmdm.ui.timetable

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
import com.example.elormovpmdm.databinding.FragmentTimetableBinding
import com.example.elormovpmdm.domain.model.UserResponse
import com.example.elormovpmdm.ui.timetable.adapter.TimetableAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimetableFragment : Fragment() {

    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!
    private val timetableViewModel: TimetableViewModel by viewModels()
    private lateinit var timetableAdapter: TimetableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimetableBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        initUI()
    }

    private fun initComponents() {
        timetableAdapter = TimetableAdapter(onItemSelected = { onItemSelected(it) })
        binding.rvTimetable.layoutManager = GridLayoutManager(context, 1)
        binding.rvTimetable.adapter = timetableAdapter
    }

    private fun onItemSelected(user: UserResponse) {
                
    }

    private fun initUI() {
        lifecycleScope.launch { 
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                timetableViewModel.users.collect { 
                    timetableAdapter.updateList(it)
                }
            }
        }
    }
}