package com.muratozturk.metflix.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.muratozturk.metflix.R
import com.muratozturk.metflix.databinding.FragmentHomeBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}