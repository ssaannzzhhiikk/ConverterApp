package com.sanzh.converterapp.presentation.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanzh.converterapp.R
import com.sanzh.converterapp.presentation.model.CurrencyUiModel

class CurrencyAdapter : ListAdapter<CurrencyUiModel, CurrencyAdapter.CurrencyViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CurrencyUiModel>() {
            override fun areItemsTheSame(old: CurrencyUiModel, new: CurrencyUiModel) =
                old.code == new.code
            override fun areContentsTheSame(old: CurrencyUiModel, new: CurrencyUiModel) =
                old == new
        }

        private val COLOR_GREEN = Color.parseColor("#C8E6C9")
        private val COLOR_RED = Color.parseColor("#FFCDD2")
    }

    inner class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCode: TextView = itemView.findViewById(R.id.tvCode)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvRate: TextView = itemView.findViewById(R.id.tvRate)
        private val tvChange: TextView = itemView.findViewById(R.id.tvChange)

        fun bind(item: CurrencyUiModel) {
            tvCode.text = item.code
            tvName.text = item.name
            tvRate.text = item.rate
            tvChange.text = item.change24h
            itemView.setBackgroundColor(
                if (item.isPositiveChange) COLOR_GREEN else COLOR_RED
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}