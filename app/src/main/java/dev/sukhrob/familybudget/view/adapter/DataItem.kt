package dev.sukhrob.familybudget.view.adapter

import dev.sukhrob.familybudget.data.model.Transaction

sealed class DataItem {

    abstract val id: Int

    data class HeaderData(
        var createdAt: String,
        override val id: Int = (createdAt.length.toLong() / 10000L).toInt()
    ) : DataItem()

    data class TransactionData(
        var transaction: Transaction,
        override val id: Int = transaction.id
    ) : DataItem()

}
