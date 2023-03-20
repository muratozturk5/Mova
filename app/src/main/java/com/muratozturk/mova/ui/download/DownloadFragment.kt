package com.muratozturk.mova.ui.download

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.common.*
import com.muratozturk.mova.common.enums.MediaTypeEnum
import com.muratozturk.mova.data.model.local.Download
import com.muratozturk.mova.databinding.FragmentDownloadBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class DownloadFragment : Fragment(R.layout.fragment_download) {
    private val binding by viewBinding(FragmentDownloadBinding::bind)
    private val viewModel: DownloadViewModel by viewModels()
    private var adapter: DownloadAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        collectData()
        filterResult()
    }

    fun initUI() {
        with(viewModel) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            getDownloads()
        }
    }

    private fun onClickItem(id: Int, mediaType: MediaTypeEnum) {
        val action = DownloadFragmentDirections.actionDownloadFragmentToDetailsFragment(
            id,
            mediaType
        )
        findNavController().navigate(action)
    }

    private fun onClickItemPlay(id: Int, mediaType: MediaTypeEnum) {
        val action = DownloadFragmentDirections.actionDownloadFragmentToVideoPlayerFragment(
            videoId = null,
            mediaType,
            id
        )
        findNavController().navigate(action)
    }

    private fun onClickItemRemove(item: Download, position: Int) {

        val action = DownloadFragmentDirections.actionDownloadFragmentToDeleteDownloadFragment(
            item,
            position
        )
        findNavController().navigate(action)
    }

    private fun collectData() {
        with(viewModel) {
            with(binding) {

                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                    downloads.collectLatest { response ->
                        when (response) {
                            is Resource.Loading -> {
                                downloadLoading.visible()
                                downloadLoading.startShimmer()
                                downloadRecyclerView.gone()
                                emptyList.gone()
                            }
                            is Resource.Error -> {
                                downloadRecyclerView.gone()

                                requireActivity().showToast(
                                    getString(R.string.error),
                                    response.throwable.localizedMessage ?: "Error",
                                    MotionToastStyle.ERROR
                                )

                            }
                            is Resource.Success -> {
                                downloadLoading.gone()
                                downloadLoading.stopShimmer()


                                if (response.data.isEmpty()) {
                                    downloadRecyclerView.gone()
                                    emptyList.visible()

                                } else {
                                    downloadRecyclerView.visible()
                                    emptyList.gone()

                                    adapter =
                                        DownloadAdapter(response.data as ArrayList)
                                    downloadRecyclerView.adapter = adapter
                                    adapter!!.onClickHigh = ::onClickItem
                                    adapter!!.onClickPlayHigh = ::onClickItemPlay
                                    adapter!!.onClickRemoveHigh = ::onClickItemRemove
                                }


                            }
                        }


                    }
                }

            }
        }
    }

    private fun filterResult() {
        setFragmentResultListener(Constants.Arguments.POP_UP) { _, bundle ->

            val position = bundle.getInt(Constants.Arguments.DELETED_POSITION)

            adapter?.removeItem(position)
            if (adapter?.itemCount!! == 0) {
                binding.emptyList.visible()
                binding.downloadRecyclerView.gone()
            }


        }
    }
}