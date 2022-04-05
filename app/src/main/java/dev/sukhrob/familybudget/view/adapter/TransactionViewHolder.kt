package dev.sukhrob.familybudget.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.sukhrob.familybudget.R
import dev.sukhrob.familybudget.databinding.ItemHeaderBinding
import dev.sukhrob.familybudget.databinding.ItemTransactionLayoutBinding
import uzbekSum

sealed class TransactionViewHolder(open val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class TransactionDataViewHolder(override val binding: ItemTransactionLayoutBinding): TransactionViewHolder(binding) {

        fun bind(item: DataItem.TransactionData) {
            with(binding) {
                transactionName.text = item.transaction.title
                transactionCategory.text = item.transaction.tag
                when (item.transaction.transactionType) {
                    "Income" -> {
                        transactionAmount.setTextColor(
                            ContextCompat.getColor(
                                transactionAmount.context,
                                R.color.income
                            )
                        )

                        transactionAmount.text = "+ ".plus(uzbekSum(item.transaction.amount))
                    }
                    "Expense" -> {
                        transactionAmount.setTextColor(
                            ContextCompat.getColor(
                                transactionAmount.context,
                                R.color.expense
                            )
                        )
                        transactionAmount.text = "- ".plus(uzbekSum(item.transaction.amount))
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): TransactionDataViewHolder {
                val binding = ItemTransactionLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return TransactionDataViewHolder(binding)
            }
        }

    }

    class HeaderDataViewHolder(override val binding: ItemHeaderBinding): TransactionViewHolder(binding) {

        fun bind(item: DataItem.HeaderData) {
            binding.dateSection.text = item.createdAt
        }

        companion object {
            fun from(parent: ViewGroup): HeaderDataViewHolder {
                val binding = ItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return HeaderDataViewHolder(binding)
            }
        }

    }

}
