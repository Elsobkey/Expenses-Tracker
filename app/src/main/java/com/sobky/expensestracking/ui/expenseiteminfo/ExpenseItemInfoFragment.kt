package com.sobky.expensestracking.ui.expenseiteminfo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sobky.expensestracking.ExpenseActivity
import com.sobky.expensestracking.R
import com.sobky.expensestracking.data.db.entity.Category
import com.sobky.expensestracking.databinding.FragmentExpenseItemInfoBinding
import com.sobky.expensestracking.utils.InjectorUtils
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*


class ExpenseItemInfoFragment : Fragment() {

    private lateinit var binding: FragmentExpenseItemInfoBinding

    private val args: ExpenseItemInfoFragmentArgs by navArgs()

    private var spinnerAdapterSet: Boolean = false


    private val viewModel: ExpenseItemInfoViewModel by viewModels {
        InjectorUtils.provideExpenseItemInfoViewModelFactory(
            requireActivity(),
            args.expenseId,
            args.expenseItemId
        )
    }

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // update toolbar home button and title...
        (requireActivity() as ExpenseActivity).setHomeButtonEnabled(true)
        (requireActivity() as ExpenseActivity).setToolbarTitle(getString(R.string.str_item_info_lbl))

        binding = FragmentExpenseItemInfoBinding.inflate(
            inflater,
            container,
            false
        )
        // expense created date click listener...
        binding.tvExpenseItemInfoDate.setOnClickListener {
            onExpenseCreatedDateClicked()
        }
        // expense created time click listener...
        binding.tvExpenseItemInfoTime.setOnClickListener {
            onExpenseCreatedTimeClicked()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        addViewsObserver()

        lifecycleScope.launch {

        }
    }

    @Suppress("LABEL_NAME_CLASH")
    private fun setupObserver() {

        // categories must observed first...
        viewModel.categories.observe(viewLifecycleOwner) { categoriesList ->
            setSpinnerAdapter(categories = categoriesList)
            viewModel.expenseItem?.observe(viewLifecycleOwner) {
                Log.v(TAG,"entered")
                if (it == null) {
                    Toast.makeText(
                        requireActivity(),
                        "Null expense item while creating",
                        LENGTH_SHORT
                    ).show()
                    findNavController().navigateUp()
                    return@observe
                }
                binding.viewModel = viewModel
                setSpinnerAdapter(selectedCategoryId = it.categoryId)
            }
        }
    }


    private fun setSpinnerAdapter(
        categories: List<Category> = emptyList(),
        selectedCategoryId: Int = 0
    ) {
        if (categories.isNotEmpty() && !spinnerAdapterSet) {
            val categoriesNames: MutableList<String> = mutableListOf()
            for (index in categories.indices) {
                categoriesNames += categories[index].categoryName
            }

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.drop_down_list_item,
                categoriesNames
            )
            (binding.spinnerExpenseItemInfoCategory).setAdapter(adapter)
            spinnerAdapterSet = true
        }

        //TODO try to make set spinner selected category using binding
        val adapter = binding.spinnerExpenseItemInfoCategory.adapter
        if (adapter != null && adapter.count > 0 && selectedCategoryId > 0) {
            //Log.v(TAG, "apply selected category item")
            outer@ for (index in viewModel.categories.value!!.indices) {
                if (viewModel.categories.value!![index].id == selectedCategoryId) {
                    binding.spinnerExpenseItemInfoCategory.setText(
                        viewModel.categories.value!![index].categoryName, false
                    )
                    break@outer
                }
            }
        }
    }

    private fun addViewsObserver() {

        binding.etExpenseItemInfoTitle.doOnTextChanged { inputText, _, _, _ ->
            if (inputText != viewModel.expenseItem?.value?.expenseName.toString()) {
                viewModel.expenseItem?.value?.expenseName = inputText.toString()
                viewModel.updateExpenseItem()
            }
        }

        binding.etExpenseItemInfoAmount.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.toString() != viewModel.expenseItem?.value?.amount.toString().trim()) {
                viewModel.expenseItem?.value?.amount = inputText.toString().toDouble()
                viewModel.updateExpenseItem()
            }
        }

        binding.etExpenseItemInfoDescription.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.toString() != viewModel.expenseItem?.value?.description.toString()
                    .trim()
            ) {
                viewModel.expenseItem?.value?.description = inputText.toString()
                viewModel.updateExpenseItem()
            }
        }

        binding.etExpenseItemInfoPrice.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.toString() != viewModel.expenseItem?.value?.price.toString().trim()) {
                viewModel.expenseItem?.value?.price = inputText.toString().toDouble()
                viewModel.updateExpenseItem()
            }
        }

        binding.etExpenseItemInfoPlaceName.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.toString() != viewModel.expenseItem?.value?.placeName.toString().trim()) {
                viewModel.expenseItem?.value?.placeName = inputText.toString()
                viewModel.updateExpenseItem()
            }
        }

        binding.etExpenseItemInfoPlaceAddress.doOnTextChanged { inputText, _, _, _ ->
            if (inputText.toString() != viewModel.expenseItem?.value?.placeAddress.toString()
                    .trim()
            ) {
                viewModel.expenseItem?.value?.placeAddress = inputText.toString()
                viewModel.updateExpenseItem()
            }
        }

        binding.spinnerExpenseItemInfoCategory.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, pos, _ ->
                viewModel.categories.value?.let { categories ->
                    viewModel.expenseItem?.value?.categoryId = categories[pos].id
                    viewModel.updateExpenseItem()
                }
            }
    }

    private fun onExpenseCreatedDateClicked() {
        var expenseYear = 0
        var expenseMonth = 0
        var expenseDay = 0
        viewModel.expenseItem?.value!!.expenseItemCreatedDate.let {
            expenseYear = it.get(Calendar.YEAR)
            expenseMonth = it.get(Calendar.MONTH)
            expenseDay = it.get(Calendar.DAY_OF_MONTH)
        }

        DatePickerDialog(
            requireActivity(),
            { _, year, monthOfYear, dayOfMonth ->
                expenseYear = year
                expenseMonth = monthOfYear
                expenseDay = dayOfMonth
                viewModel.updateExpenseCreatedDate(expenseYear, expenseMonth, expenseDay)
            }, expenseYear, expenseMonth, expenseDay
        ).also {
            it.show()
        }
    }

    private fun onExpenseCreatedTimeClicked() {
        var expenseHour = 0
        var expenseMinute = 0
        viewModel.expenseItem?.value!!.expenseItemCreatedDate.let {
            expenseHour = it.get(Calendar.HOUR_OF_DAY)
            expenseMinute = it.get(Calendar.MINUTE)
        }
        val datePickerDialog = TimePickerDialog(
            requireActivity(), { _, hour, minute ->
                expenseHour = hour
                expenseMinute = minute
                viewModel.updateExpenseCreateTime(expenseHour, expenseMinute)
            }, expenseHour, expenseMinute, false
        )
        datePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "ExpenseItemInfoFragment"
    }
}