package com.muratozturk.metflix.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.muratozturk.metflix.R
import com.muratozturk.metflix.common.*
import com.muratozturk.metflix.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setupBottomNavigationView()
        collectData()
    }

    private fun collectData() {
        with(viewModel) {

            lifecycleScope.launchWhenCreated {
                darkMode.collectLatest { response ->
                    when (response) {
                        is Resource.Loading -> {
                        }
                        is Resource.Error -> {
                            showToast(
                                getString(R.string.error),
                                response.throwable.localizedMessage ?: "Error",
                                MotionToastStyle.ERROR
                            )

                        }
                        is Resource.Success -> {
                            Timber.e(response.data.toString())

                            try {
                                if (response.data) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                }
                            } catch (e: Exception) {
                                Timber.e(e)
                            }

                        }
                    }
                }
            }
        }
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