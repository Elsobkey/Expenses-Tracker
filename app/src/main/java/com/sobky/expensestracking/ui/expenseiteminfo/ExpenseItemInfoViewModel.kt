package com.sobky.expensestracking.ui.expenseiteminfoimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport androidx.lifecycle.ViewModelimport androidx.lifecycle.viewModelScopeimport com.sobky.expensestracking.data.db.entity.Categoryimport com.sobky.expensestracking.data.db.entity.ExpenseItemimport com.sobky.expensestracking.data.repository.CategoryRepositoryimport com.sobky.expensestracking.data.repository.ExpenseRepositoryimport kotlinx.coroutines.*import java.text.SimpleDateFormatimport java.util.*class ExpenseItemInfoViewModel(    private var expenseId: Long = 0L,    private var expenseItemId: Long = 0L,    private val repository: ExpenseRepository,    categoryRepository: CategoryRepository) : ViewModel() {    private var _expenseItem: MutableLiveData<ExpenseItem>? = MutableLiveData()    val expenseItem: LiveData<ExpenseItem?>?        get() = _expenseItem    init {        viewModelScope.launch(Dispatchers.Default) {            if (expenseItemId == 0L) {                expenseItemId = withContext(Dispatchers.Default) {                    repository.createExpenseItem(ExpenseItem(expenseId = expenseId))                }                if (expenseItemId == 0L) {                    this.cancel("ExpenseItem was not created")                    _expenseItem?.postValue(null)                }            }            withContext(Dispatchers.Default) {                val createdExpenseItem = repository.getExpenseItem(expenseItemId)                _expenseItem?.postValue(createdExpenseItem) // To make sure that expense item was created...            }        }    }    /** update expense created date to [ExpenseItem] table...*/    fun updateExpenseCreatedDate(expenseYear: Int, expenseMonth: Int, expenseDay: Int) {        _expenseItem?.value?.let {            it.expenseItemCreatedDate.set(Calendar.YEAR, expenseYear)            it.expenseItemCreatedDate.set(Calendar.MONTH, expenseMonth)            it.expenseItemCreatedDate.set(Calendar.DAY_OF_MONTH, expenseDay)            updateExpenseItem(true)        }    }    /** update expense created date to [ExpenseItem] table...*/    fun updateExpenseCreateTime(expenseHour: Int, expenseMinute: Int) {        _expenseItem?.value?.let {            it.expenseItemCreatedDate.set(Calendar.HOUR_OF_DAY, expenseHour)            it.expenseItemCreatedDate.set(Calendar.MINUTE, expenseMinute)            updateExpenseItem(true)        }    }    fun updateExpenseItem(setLiveDataValue: Boolean = false) {        var updatedRows = 0        if (_expenseItem?.value != null) {            viewModelScope.launch {                updatedRows = repository.updateExpenseItem(_expenseItem?.value!!)                if (setLiveDataValue && updatedRows > 0) {                    //Log.v(TAG, "updated No. of rows:$updatedRows")                    // set LiveData value again to be observed in the view that called it...                    _expenseItem?.value = _expenseItem?.value                }            }        }    }    var categories: LiveData<List<Category>> = categoryRepository.getCategories()    fun expenseDateString(): String {        return dateFormat.format(_expenseItem?.value?.expenseItemCreatedDate!!.time)    }    fun expenseTimeString(): String {        return timeFormat.format(_expenseItem?.value?.expenseItemCreatedDate!!.time)    }    override fun onCleared() {        super.onCleared()        deleteEmptyExpenseItem()    }    private fun deleteEmptyExpenseItem() {        _expenseItem?.value?.let { expenseItemRow ->            if (expenseItemRow.expenseName.isEmpty() && expenseItemRow.description.isEmpty()                && (expenseItemRow.amount <= 0)                && (expenseItemRow.price <= 0)                && expenseItemRow.placeName.isEmpty() && expenseItemRow.placeAddress.isEmpty()            ) {                GlobalScope.launch {                    repository.deleteExpenseItem(expenseItemRow)                    //val updatedRows =                    //if (updatedRows > 0) {                    //Log.v(TAG, "Deleted No. of rows:$updatedRows")                }            }        }    }    companion object {        const val TAG: String = "ExpenseItemInfoViewMode"        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)        val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)    }}