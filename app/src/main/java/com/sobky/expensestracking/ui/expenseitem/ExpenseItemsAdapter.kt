package com.sobky.expensestracking.ui.expenseitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.sobky.expensestracking.ExpenseItemsFragmentDirections
import com.sobky.expensestracking.data.db.relation.ExpenseItemAndCategory
import com.sobky.expensestracking.databinding.ListItemExpenseItemBinding

/**
 * Adapter for the [RecyclerView] in [com.sobky.expensestracking.ExpenseItemsFragment].
 */
class ExpenseItemsAdapter : ListAdapter<ExpenseItemAndCategory, RecyclerView.ViewHolder>(
    ExpenseItemsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExpenseItemsViewHolder(
            ListItemExpenseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val expense: ExpenseItemAndCategory = getItem(position)
        (holder as ExpenseItemsViewHolder).bind(expense)
    }
}

class ExpenseItemsViewHolder(val binding: ListItemExpenseItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.setClickListener { view ->
            binding.expenseItem?.let { expenseItem ->
                navigateToExpenseItemInfo(expenseItem, view)
            }
        }
    }

    private fun navigateToExpenseItemInfo(
        expenseItem: ExpenseItemAndCategory,
        view: View
    ) {
        //Log.v(TAG, "navigate" + expenseItem.id)
        val direction = ExpenseItemsFragmentDirections
            .actionExpenseItemFragmentToExpenseItemInfoFragment(
                expenseItem.expenseItem.id, //TODO
                expenseItem.expenseItem.id
            )
        view.findNavController().navigate(direction)
    }

    fun bind(item: ExpenseItemAndCategory) {
        binding.apply {
            binding.expenseItem = item
            executePendingBindings()
        }
    }


    companion object {
        const val TAG = "ExpensesAdapter"
    }
}

private class ExpenseItemsDiffCallback : DiffUtil.ItemCallback<ExpenseItemAndCategory>() {

    override fun areItemsTheSame(
        oldItem: ExpenseItemAndCategory,
        newItem: ExpenseItemAndCategory
    ): Boolean {
        return oldItem.expenseItem.id == newItem.expenseItem.id
    }

    override fun areContentsTheSame(
        oldItem: ExpenseItemAndCategory,
        newItem: ExpenseItemAndCategory
    ): Boolean {
        return oldItem == newItem
    }

}



