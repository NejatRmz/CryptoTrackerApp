package com.example.cryptotrackerapp.presentation.coin_list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotrackerapp.R
import com.example.cryptotrackerapp.common.OnItemClick
import com.example.cryptotrackerapp.common.Sparkline
import com.example.cryptotrackerapp.common.drawChart
import com.example.cryptotrackerapp.databinding.CoinListItemBinding
import com.example.cryptotrackerapp.domain.model.Coin

class CoinListAdapter(private val coinList: ArrayList<Coin>) :
    RecyclerView.Adapter<CoinListAdapter.CoinListHolder>(){

    private var itemClick: OnItemClick? = null
    private lateinit var binding: CoinListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
        binding = CoinListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinListHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        val coin: Coin = coinList[position]
        holder.bind(coin)
        holder.itemView.setOnClickListener {
            Log.d(TAG, coin.id)
            try {
                binding.root.findNavController().navigate(R.id.navigation_coin_detail)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

        holder.itemBinding.textHour.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.HOUR.sparkline)
        }

        holder.itemBinding.textDay.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.DAY.sparkline)
        }

        holder.itemBinding.textWeek.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.WEEK.sparkline)
        }

        holder.itemBinding.text2Week.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.TWO_WEEK.sparkline)
        }

        holder.itemBinding.textMonth.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.MONTH.sparkline)
        }

        holder.itemBinding.text200D.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.HALF_YEAR.sparkline)
        }

        holder.itemBinding.textYear.setOnClickListener {
            itemClick?.onItemClicked("usd", Sparkline.YEAR.sparkline)
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    fun updateCoinDataList(newList: List<Coin>) {
        coinList.clear()
        coinList.addAll(newList)
        notifyDataSetChanged()
    }

    class CoinListHolder(val itemBinding: CoinListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(coin: Coin) {
            coin.apply {
                Log.d(TAG, coin.name)
                itemBinding.abb.text = coin.symbol.uppercase()
                itemBinding.name.text = coin.id.uppercase()

                if (coin.sparkline.price.size > 0) {
                    drawChart(itemBinding.dataChart, coin.sparkline.price)
                }

            }
        }
    }

    companion object {
        private const val TAG = "CoinListAdapter"
    }

    fun setItemClick(itemClick: OnItemClick?) {
        this.itemClick = itemClick
    }
}
