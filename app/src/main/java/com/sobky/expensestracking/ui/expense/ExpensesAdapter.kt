package com.sobky.expensestracking.ui.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems
import com.sobky.expensestracking.databinding.ListItemExpenseBinding

/**
 * Adapter for the [RecyclerView] in [com.sobky.expensestracking.ExpensesFragment].
 */
class ExpensesAdapter(val expenseCallback: ExpenseCallback) : ListAdapter<ExpenseAndExpenseItems,
        RecyclerView.ViewHolder>(ExpensesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpensesViewHolder(
            ListItemExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val expense: ExpenseAndExpenseItems = getItem(position)
        (holder as ExpensesViewHolder).bind(expense)
    }

    interface ExpenseCallback {
        fun onExpenseClicked(expense: ExpenseAndExpenseItems)
    }

    internal inner class ExpensesViewHolder(val binding: ListItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.expense?.let { expense ->
                    expenseCallback.onExpenseClicked(expense)
                    //navigateToExpenseItems(expense, view)
                }
            }
        }

        fun bind(item: ExpenseAndExpenseItems) {
            binding.apply {
                binding.expense = item
                executePendingBindings()
            }
        }

        private fun navigateToExpenseItems(
            expense: ExpenseAndExpenseItems,
            view: View
        ) {
            //Log.v(TAG, "navigate" + expense.expense.id)
            val direction = ExpensesFragmentDirections
                .actionExpensesFragToExpenseItemsFrag(expense.expense.id)
            view.findNavController().navigate(direction)
        }
    }

    companion object {
        const val TAG = "ExpensesAdapter"
    }

    private class ExpensesDiffCallback : DiffUtil.ItemCallback<ExpenseAndExpenseItems>() {

        override fun areItemsTheSame(
            oldItem: ExpenseAndExpenseItems,
            newItem: ExpenseAndExpenseItems
        ): Boolean {
            return oldItem.expense.id == newItem.expense.id
        }

        override fun areContentsTheSame(
            oldItem: ExpenseAndExpenseItems,
            newItem: ExpenseAndExpenseItems
        ): Boolean {
            return oldItem == newItem
        }
    }
}



