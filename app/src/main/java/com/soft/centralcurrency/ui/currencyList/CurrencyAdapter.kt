package com.soft.centralcurrency.ui.currencyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.centralcurrency.BR
import com.soft.centralcurrency.R
import com.soft.centralcurrency.data.dto.ExchangeRates

class CurrencyAdapter : ListAdapter<ExchangeRates, CurrencyAdapter.CurrencyViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ExchangeRates>() {
            override fun areItemsTheSame(oldItem: ExchangeRates, newItem: ExchangeRates): Boolean {
                return oldItem.shortName == newItem.shortName
            }

            override fun areContentsTheSame(
                oldItem: ExchangeRates,
                newItem: ExchangeRates
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.layout_currency, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CurrencyViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: ExchangeRates) {
            binding.setVariable(BR.viewModel, rate)
        }
    }


}