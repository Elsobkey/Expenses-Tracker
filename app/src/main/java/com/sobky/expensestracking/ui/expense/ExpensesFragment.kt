package com.sobky.expensestracking.ui.expense

//import androidx.lifecycle.observe
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sobky.expensestracking.ExpenseActivity
import com.sobky.expensestracking.R
import com.sobky.expensestracking.databinding.DialogTitleViewBinding
import com.sobky.expensestracking.databinding.FragmentExpensesBinding
import com.sobky.expensestracking.utils.InjectorUtils

class ExpensesFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentExpensesBinding

    private val viewModel: ExpensesViewModel by viewModels {
        InjectorUtils.provideExpensesViewModelFactory(requireActivity())
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
        val adapter = ExpensesAdapter()
        binding.rvExpenses.adapter = adapter
        subscribeUi(adapter)
    }

    override fun onResume() {
        super.onResume()
        // call this in onResume method as this fragment called before binding the parent activity
        (requireActivity() as ExpenseActivity).setHomeButtonEnabled(false)
        (requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.app_name))
    }

    private fun subscribeUi(adapter: ExpensesAdapter) {
        viewModel.expenses.observe(viewLifecycleOwner) { expensesList ->
            adapter.submitList(expensesList)
        }
    }

    override fun onClick(v: View?) {
        if ((v == (binding.fabAddNewExpense))) {
            //showExpenseTitleDialog()
            val direction = ExpensesFragmentDirections.actionExpensesFragToExpenseItemsFrag(expenseId = 0,expenseTitle = "")
            findNavController().navigate(direction)

        }
    }

    private fun showExpenseTitleDialog() {
        val builder = MaterialAlertDialogBuilder(requireActivity())
        builder.setTitle(getString(R.string.str_dlg_expense_title_lbl))
        builder.setCancelable(true)

        val dialogTitleViewBinding: DialogTitleViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.dialog_title_view,
            null,
            false
        )

        // dialog message view
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_title_view, null)
        builder.setView(dialogTitleViewBinding.root)

        builder.setNegativeButton(getString(R.string.str_dlg_dismiss_lbl), null)
        builder.setPositiveButton(getString(R.string.str_dlg_submit_lbl)) { dialog, _ ->
            val expenseTitle: String = dialogTitleViewBinding.etDialogExpenseTitle.text.toString()
            val navHostFragment: NavHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.frag_expenses) as NavHostFragment
            val frag: ExpensesFragment =
                navHostFragment.childFragmentManager.fragments[0] as ExpensesFragment
            frag.createNewExpense(expenseTitle, dialog)
        }

        // show dialog...
        builder.create().show()
    }


    private fun createNewExpense(expenseTitle: String, dialog: DialogInterface) {
        viewModel.createExpense(expenseTitle)
        dialog.dismiss()
        // TODO navigate to expense items fragment...
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_expense,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val TAG = "ExpensesFragment"
    }
}