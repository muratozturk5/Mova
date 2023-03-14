package com.muratozturk.metflix.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.gone
import com.muratozturk.metflix.common.visible
import com.muratozturk.metflix.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)


        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.signUpFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.signInWithPasswordFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.onBoardingFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.signInWithSocialFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.dialogFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.videoPlayerFragment -> {
                    binding.bottomNavigation.gone()
                }
                R.id.previewImagesFragment -> {
                    binding.bottomNavigation.gone()
                }
                else -> {
                    binding.bottomNavigation.visible()
                }
            }
        }
    }


}