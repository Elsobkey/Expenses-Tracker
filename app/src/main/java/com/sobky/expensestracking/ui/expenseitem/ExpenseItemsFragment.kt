package com.sobky.expensestracking.ui.expenseitem

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sobky.expensestracking.ExpenseActivity
import com.sobky.expensestracking.R
import com.sobky.expensestracking.databinding.FragmentExpenseItemsBinding
import com.sobky.expensestracking.utils.InjectorUtils

class ExpenseItemsFragment : Fragment() {

    private lateinit var binding: FragmentExpenseItemsBinding

    private val args: ExpenseItemsFragmentArgs by navArgs()

    private val viewModel: ExpenseItemsViewModel by viewModels {
        InjectorUtils.provideExpenseItemsViewModelFactory(requireActivity(), args.expenseId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseItemsBinding.inflate(inflater, container, false)

        // if it was already created expense just set its title, else create new one expense...
        viewModel.currentExpense.observe(viewLifecycleOwner) { expense ->
            binding.etExpenseItemsExpenseTitle.setText(expense.expenseTitle)
            (requireActivity() as ExpenseActivity).currentExpenseId = expense.id
        }

        // update toolbar home button and title...
        (requireActivity() as ExpenseActivity).setHomeButtonEnabled(true)
        (requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.str_expense_items_lbl))

        setHasOptionsMenu(true) // showing option menu in this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAddNewExpenseItem.setOnClickListener {
            onAddNewExpenseItemFabClicked()
        }

        val adapter = ExpenseItemsAdapter()
        binding.rvExpenseItems.adapter = adapter
        subscribeUi(adapter)
        observeExpenseTitle()
    }

    private fun observeExpenseTitle() {
        binding.etExpenseItemsExpenseTitle.doAfterTextChanged { inputText: Editable? ->
            if (inputText.toString() != viewModel.currentExpense.value?.expenseTitle) { //TODO
                viewModel.updateExpenseTitle(inputText.toString())
            }
        }
    }

    private fun subscribeUi(adapter: ExpenseItemsAdapter) {
        viewModel.expenseItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onAddNewExpenseItemFabClicked() {
        viewModel.currentExpense.value.let { expense ->
            if (expense != null) {
                val direction = ExpenseItemsFragmentDirections
                    .actionExpenseItemFragmentToExpenseItemInfoFragment(
                        expenseId = expense.id,
                        0
                    )
                findNavController().navigate(direction)
            }
        }
    }


    companion object {
        const val TAG: String = "ExpenseItemsFragment"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_expense_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
//            viewModel.currentExpense.value?.let {
//                val navDirection = ExpenseItemsFragmentDirections
//                    .actionExpenseItemsFragmentBackToExpenseFrag(expenseId = it.id)
//                findNavController().navigate(navDirection)
//            } ?: findNavController().navigateUp() // expense may be not created yet.
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

