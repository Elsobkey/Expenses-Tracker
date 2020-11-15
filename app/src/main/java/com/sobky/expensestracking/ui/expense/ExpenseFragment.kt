package com.sobky.expensestracking.ui.expense

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.sobky.expensestracking.ExpenseActivity
import com.sobky.expensestracking.R
import com.sobky.expensestracking.data.db.relation.ExpenseAndExpenseItems
import com.sobky.expensestracking.databinding.FragmentExpensesBinding
import com.sobky.expensestracking.utils.InjectorUtils

class ExpenseFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentExpensesBinding

    private val viewModel: ExpenseViewModel by viewModels {
        InjectorUtils.provideExpenseViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.app_name))
        binding = FragmentExpensesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        binding.fabAddNewExpense.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //(requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.app_name))
        val adapter = ExpenseAdapter(object : ExpenseAdapter.ExpenseCallback {
            override fun onExpenseClicked(expense: ExpenseAndExpenseItems) {
                val expenseId = expense.expense.id
                onExpenseItemClicked(expenseId)
            }
        })

        binding.rvExpenses.adapter = adapter
        subscribeUi(adapter)

        // check if current expense has empty data
        val currentExpenseId = (requireActivity() as ExpenseActivity).currentExpenseId
        if (currentExpenseId > 0) {
            viewModel.isEmptyExpense(currentExpenseId)
            subscribeDeleteEmptyExpense()
        }
    }

    override fun onResume() {
        super.onResume()
        // call this in onResume method as this fragment called before binding the parent activity
        (requireActivity() as ExpenseActivity).setHomeButtonEnabled(false)
        (requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.app_name))
    }

    private fun subscribeUi(adapter: ExpenseAdapter) {
        viewModel.expenses.observe(viewLifecycleOwner) { expensesList ->
            adapter.submitList(expensesList)
        }
    }

    private fun subscribeDeleteEmptyExpense() {

        viewModel.isEmptyExpenseObservable.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                viewModel.deleteExpense((requireActivity() as ExpenseActivity).currentExpenseId)
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.str_discard_empty_expense_msg_),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onClick(v: View?) {
        if ((v == (binding.fabAddNewExpense))) {
            val direction = ExpenseFragmentDirections
                .actionExpensesFragToExpenseItemsFrag(expenseId = 0)
            findNavController().navigate(direction)
        }
    }

    private fun onExpenseItemClicked(expenseId: Long) {
        val direction = ExpenseFragmentDirections
            .actionExpensesFragToExpenseItemsFrag(expenseId = expenseId)
        findNavController().navigate(direction)
        //(requireActivity() as ExpenseActivity).currentExpenseId = expenseId
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_expense, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "ExpensesFragment"
    }
}