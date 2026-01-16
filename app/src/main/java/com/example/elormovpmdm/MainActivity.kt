package com.example.elormovpmdm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.elormovpmdm.databinding.ActivityMainBinding
import com.example.elormovpmdm.ui.teacherProfile.ProfileActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initComponents()
        initUI()
    }
    
    private fun initUI() {
        initNavigation()
    }
    
    private fun initNavigation() {
        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomBar.setupWithNavController(navController)
        
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.timetableFragment -> binding.tvToolbarTitle.text = getString(R.string.timetable)
                R.id.studentsFragment -> binding.tvToolbarTitle.text = getString(R.string.students)
                R.id.meetingsFragment -> binding.tvToolbarTitle.text = getString(R.string.meetings)
            }
        }
    }
    
    private fun initComponents() {
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}