package com.coin.coindemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.coin.coindemo.R
import com.coin.coindemo.data.GetCoinResponse
import com.coin.coindemo.databinding.RecyclerviewHolderViewBinding

class CoinsAdapter(): RecyclerView.Adapter<CoinsAdapter.ItemViewHolder>() {

    private var coinsList: ArrayList<GetCoinResponse> = ArrayList()
    private var filteredCoinsList: ArrayList<GetCoinResponse> = ArrayList()

    inner class ItemViewHolder(val binding : RecyclerviewHolderViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RecyclerviewHolderViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder) {
            with(coinsList[position]) {
                binding.coinName.text = this.name
                binding.coinSymbol.text = this.symbol
                binding.coinType.text = this.type
                if (this.type == "coin") {
                    binding.coinTypeIcon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.coin))
                } else {
                    binding.coinTypeIcon.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.token))
                }
                binding.root.alpha = if (this.isActive) 1.0f else 0.5f
            }
        }
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    fun addData(list: List<GetCoinResponse>) {
        coinsList = list as ArrayList<GetCoinResponse>
        filteredCoinsList = coinsList
        notifyDataSetChanged()
    }
}