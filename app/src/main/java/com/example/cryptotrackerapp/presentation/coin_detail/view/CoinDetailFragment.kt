package com.example.cryptotrackerapp.presentation.coin_detail.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.work.*
import com.example.cryptotrackerapp.R
import com.example.cryptotrackerapp.common.AlertWorker
import com.example.cryptotrackerapp.common.autoCleared
import com.example.cryptotrackerapp.databinding.FragmentCoinDetailBinding
import com.example.cryptotrackerapp.presentation.coin_detail.viewmodel.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private lateinit var viewModel: CoinDetailViewModel

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

        }
        val root: View = binding.root

        val downloadRequest = OneTimeWorkRequestBuilder<AlertWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .build()

        val workManager = WorkManager.getInstance(requireContext())


        binding.btnSetAlarm.setOnClickListener {
            workManager
                .beginUniqueWork(
                    "download",
                    ExistingWorkPolicy.KEEP,
                    downloadRequest
                )
                .enqueue()

            setPeriodicallySendingLogs()
            val maxPrice = binding.etMaxPrice.text.toString()
            val minPrice = binding.etMinPrice.text.toString()
            viewModel.saveToDataStore(maxPrice, minPrice)
        }


        viewModel.readFromDataStore.observe(requireActivity()) { it ->
            Log.e(TAG, "Max Value ${it[0]}")
            Log.e(TAG, "Min Value ${it[1]}")
            binding.maxPrice.hint = it.get(0)
            binding.minPrice.hint = it.get(1)
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

    companion object {
        private const val TAG = "CoinDetailFragment"
    }
    private fun setPeriodicallySendingLogs() {

        val workManager = WorkManager.getInstance(requireContext())
        val sendingLog = PeriodicWorkRequestBuilder<AlertWorker>(15, TimeUnit.MINUTES)
            .addTag(TAG)
            .build()

        workManager.enqueue(sendingLog)

        workManager.getWorkInfoByIdLiveData(sendingLog.id)
            .observe(requireActivity(), Observer { workInfo ->
                binding.btnShowHistory.text = workInfo.outputData.toString()
            })
    }

}