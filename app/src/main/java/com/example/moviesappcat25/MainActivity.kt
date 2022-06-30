package com.example.moviesappcat25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesappcat25.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_container)
        val appConfig = AppBarConfiguration(
            setOf(R.id.PayingNowFragment, R.id.UpcomingFragment, R.id.PopularFragment)
        )

        setupActionBarWithNavController(navController, appConfig)
        binding.navigationBar.setupWithNavController(navController)
    }
}