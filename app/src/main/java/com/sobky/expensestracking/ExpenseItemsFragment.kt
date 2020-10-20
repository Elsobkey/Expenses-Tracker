package com.sobky.expensestracking

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sobky.expensestracking.adapters.ExpenseItemsAdapter
import com.sobky.expensestracking.databinding.FragmentExpenseItemsBinding
import com.sobky.expensestracking.util.InjectorUtils
import com.sobky.expensestracking.viewmodels.ExpenseItemsViewModel

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

        (requireActivity() as ExpenseActivity).setHomeButtonEnabled(true)
        (requireActivity() as ExpenseActivity).setToolbarTitle(args.expenseTitle)

        binding = FragmentExpenseItemsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddNewExpenseItem.setOnClickListener {
            onAddNewExpenseItemFabClicked()
        }

        val adapter = ExpenseItemsAdapter()
        binding.rvExpenseItems.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: ExpenseItemsAdapter) {
        viewModel.expenseItems.observe(viewLifecycleOwner) {
                adapter.submitList(it)
        }
    }

    private fun onAddNewExpenseItemFabClicked() {
        val direction =
            ExpenseItemsFragmentDirections.actionExpenseItemFragmentToExpenseItemInfoFragment(
                args.expenseId,
                0
            )
        findNavController().navigate(direction)
    }

    companion object {
        const val TAG: String = "ExpenseItemsFragment"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExpenseItemsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_expense_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

