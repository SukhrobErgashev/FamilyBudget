package dev.sukhrob.familybudget.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.familybudget.R
import dev.sukhrob.familybudget.databinding.FragmentAddTransactionBinding
import dev.sukhrob.familybudget.data.model.Transaction
import dev.sukhrob.familybudget.utils.Constants
import dev.sukhrob.familybudget.view.base.BaseFragment
import dev.sukhrob.familybudget.view.main.viewmodel.TransactionViewModel
import parseDouble
import snack
import transformIntoDatePicker
import java.util.*

@AndroidEntryPoint
class AddTransactionFragment :
    BaseFragment<FragmentAddTransactionBinding, TransactionViewModel>() {
    override val viewModel: TransactionViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        val transactionTypeAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_autocomplete_layout,
                Constants.transactionType
            )
        val tagsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_autocomplete_layout,
            Constants.transactionTags
        )

        with(binding) {
            // Set list to TextInputEditText adapter
            addTransactionLayout.etTransactionType.setAdapter(transactionTypeAdapter)
            addTransactionLayout.etTag.setAdapter(tagsAdapter)

            // Transform TextInputEditText to DatePicker using Ext function
            addTransactionLayout.etWhen.transformIntoDatePicker(
                requireContext(),
                "dd/MM/yyyy",
                Date()
            )
            btnSaveTransaction.setOnClickListener {
                binding.addTransactionLayout.apply {
                    val (title, amount, transactionType, tag, date, note) = getTransactionContent()
                    // validate if transaction content is empty or not
                    when {
                        title.isEmpty() -> {
                            this.etTitle.error = "Sarlavha bo'sh bo'lishi mumkinmas"
                        }
                        amount.isNaN() -> {
                            this.etAmount.error = "Miqdor bo'sh bo'lishi mumkinmas"
                        }
                        transactionType.isEmpty() -> {
                            this.etTransactionType.error = "Tranzaksiya turi bo'sh bo'lishi mumkinmas"
                        }
                        tag.isEmpty() -> {
                            this.etTag.error = "Kategoriya bo'sh bo'lishi mumkinmas"
                        }
                        date.isEmpty() -> {
                            this.etWhen.error = "Sana bo'sh bo'lishi mumkinmas"
                        }
                        note.isEmpty() -> {
                            this.etNote.error = "Qo'shimcha biron nima yozing"
                        }
                        else -> {
                            viewModel.insertTransaction(getTransactionContent()).run {
                                binding.root.snack(
                                    string = R.string.success_expense_saved
                                )
                                findNavController().navigate(
                                    R.id.action_addTransactionFragment_to_dashboardFragment
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTransactionContent(): Transaction = binding.addTransactionLayout.let {
        val title = it.etTitle.text.toString()
        val amount = parseDouble(it.etAmount.text.toString())
        //val transactionType = it.etTransactionType.text.toString()
        val transactionType =
            if (it.etTransactionType.text.toString() == "Daromad") {
                "Income"
            } else {
                "Expense"
            }
        val tag = it.etTag.text.toString()
        val date = it.etWhen.text.toString()
        val note = it.etNote.text.toString()

        return Transaction(title, amount, transactionType, tag, date, note)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddTransactionBinding.inflate(inflater, container, false)
}
