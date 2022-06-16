package com.example.cryptotrackerapp.presentation.coin_list.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotrackerapp.common.Constants
import com.example.cryptotrackerapp.common.OnItemClick
import com.example.cryptotrackerapp.common.Sparkline
import com.example.cryptotrackerapp.data.remote.dto.toCoin
import com.example.cryptotrackerapp.databinding.FragmentCoinListBinding
import com.example.cryptotrackerapp.domain.model.Coin
import com.example.cryptotrackerapp.presentation.coin_list.adapter.CoinListAdapter
import com.example.cryptotrackerapp.presentation.coin_list.viewmodel.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment(), OnItemClick {

    private lateinit var binding: FragmentCoinListBinding
    private lateinit var coinListAdapter: CoinListAdapter
    private val viewModel: CoinListViewModel by viewModels()

    private var time = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coinListAdapter = CoinListAdapter(arrayListOf())
        coinListAdapter.setItemClick(this);
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(binding.root.context)
        binding.rvCoinList.layoutManager = layoutManager
        binding.rvCoinList.adapter=coinListAdapter
        getCurrencyRate()
    }

    private fun fetchData(currency: String, price_change_percentage: String){
        lifecycleScope.launch {
            viewModel.getCurrencyRate(currency, "bitcoin,ethereum,ripple", "market_cap_desc", 100, 1, true, price_change_percentage)
        }
    }

    private fun getCurrencyRate(){
        lifecycleScope.launch {
            viewModel.state.collect() {
                Log.e(TAG, "time: ${time++}")
                when {
                    it.isLoading -> {
                        Log.d(TAG,"Loading")
                    }
                    it.error.isNotBlank() -> {
                        Log.d(TAG,"Error")
                    }
                    it.coins.isNotEmpty() ->{
                        Log.d(TAG,"Success")
                        var coinList = arrayListOf<Coin>()
                        for (item in it.coins) {
                            coinList.add(item.toCoin())
                        }
                        coinListAdapter.updateCoinDataList(coinList)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CoinListFragment"
    }

    override fun onItemClicked(currency: String, price_change_percentage: String) {
        fetchData(currency, price_change_percentage)
    }

}