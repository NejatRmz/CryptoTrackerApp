package com.example.cryptotrackerapp.presentation.coin_history.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotrackerapp.common.autoCleared
import com.example.cryptotrackerapp.databinding.FragmentCoinHistoryBinding
import com.example.cryptotrackerapp.presentation.coin_detail.viewmodel.CoinDetailViewModel
import com.example.cryptotrackerapp.presentation.coin_history.viewmodel.CoinHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinHistoryFragment : Fragment() {

    private lateinit var coinHistoryViewModel: CoinHistoryViewModel
    private var binding: FragmentCoinHistoryBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinHistoryViewModel =
            ViewModelProvider(this).get(CoinHistoryViewModel::class.java)

        binding = FragmentCoinHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.text
        coinHistoryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}