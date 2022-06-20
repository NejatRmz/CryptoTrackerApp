package com.example.cryptotrackerapp.presentation.coin_detail.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.work.WorkInfo
import com.example.cryptotrackerapp.R
import com.example.cryptotrackerapp.common.Constants.KEY_IMAGE_URI
import com.example.cryptotrackerapp.common.autoCleared
import com.example.cryptotrackerapp.databinding.FragmentCoinDetailBinding
import com.example.cryptotrackerapp.presentation.coin_detail.viewmodel.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinDetailViewModel
    private lateinit var maximumValue: String
    private lateinit var minimumValue: String
    private lateinit var coinName: String

    private var binding: FragmentCoinDetailBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(CoinDetailViewModel::class.java)
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)

        arguments?.let {
            binding.coinName.text = it.getString("coin_symbol").toString().uppercase()
            binding.coinPrice.text = it.getDouble("coin_price").toString()
            coinName = it.getString("coin_name").toString().uppercase()
        }
        val root: View = binding.root

        viewModel.outputWorkInfos.observe(requireActivity(), workInfosObserver())

        binding.btnSetAlarm.setOnClickListener {
            maximumValue = binding.etMaxPrice.text.toString()
            minimumValue = binding.etMinPrice.text.toString()

            if (maximumValue.isEmpty() || minimumValue.isEmpty()) {
                Toast.makeText(requireContext(), "cannot be empty", Toast.LENGTH_SHORT).show()
            }else {
                viewModel.applyBlur(maximumValue, minimumValue, coinName)
            }
        }

        binding.btnShowHistory.setOnClickListener {
            try {
                binding.root.findNavController().navigate(R.id.navigation_coin_history)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return root
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }
            val workInfo = listOfWorkInfo[0]

            if (workInfo.state.isFinished) {
                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                Log.e(TAG, "Status: ${workInfo.state.name}")
                if (!outputImageUri.isNullOrEmpty()) {
                    Log.e(TAG, "Status: ${outputImageUri.toString()}")
                }
            } else {
                Log.e(TAG, "Status: ${workInfo.state.name}")
            }

        }
    }

    companion object {
        private const val TAG = "CoinDetail"
    }
}