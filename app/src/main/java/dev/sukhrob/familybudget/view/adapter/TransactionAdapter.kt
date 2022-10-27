package dev.sukhrob.familybudget.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.sukhrob.familybudget.data.model.Transaction
import toListOfDataItem
import java.lang.ClassCastException


class  TransactionAdapter : ListAdapter<DataItem, TransactionViewHolder>(DataItemDiffCallback()) {

    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val TRANSACTION_VIEW_TYPE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.HeaderData -> HEADER_VIEW_TYPE
            is DataItem.TransactionData -> TRANSACTION_VIEW_TYPE
        }
    }

    // on item click listener
    private var onItemClickListener: ((Transaction) -> Unit)? = null
    fun setOnItemClickListener(listener: (Transaction) -> Unit) {
        onItemClickListener = listener
    }

    fun submitDataList(list: List<Transaction>?) {
        val newDataList = list?.toListOfDataItem()
        submitList(newDataList)
    }

    /**
     * Get single Note Object from the list
     */
    fun getSingleNote(position: Int): Transaction {
        val item = getItem(position) as DataItem.TransactionData
        return item.transaction
    }

    class DataItemDiffCallback: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> TransactionViewHolder.HeaderDataViewHolder.from(parent)
            TRANSACTION_VIEW_TYPE -> TransactionViewHolder.TransactionDataViewHolder.from(parent)
            else -> throw ClassCastException("ViewType not recognized $viewType")
        }
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder.HeaderDataViewHolder -> {
                val item = getItem(position) as DataItem.HeaderData
                holder.bind(item)
            }
            is TransactionViewHolder.TransactionDataViewHolder -> {
                val item = getItem(position) as DataItem.TransactionData
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.invoke(item.transaction)
                }
            }
        }
    }
}
