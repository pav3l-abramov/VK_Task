package com.example.vktask.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktask.data.Product
import com.example.vktask.retrofit.AppRetrofit
import com.example.vktask.retrofit.GetDataApi
import com.example.vktask.retrofit.GetStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel  @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    var uiStateCurrentState = mutableStateOf(StateListUI())
        private set

    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)
    var uiCheckStatus = mutableStateOf(StatusUI())
    private val _productsState = MutableLiveData<GetStateList>()
    val productsState: LiveData<GetStateList> = _productsState

    private val _dataListProduct = MutableStateFlow<List<Product>>(emptyList())
    val dataListProduct: StateFlow<List<Product>> = _dataListProduct.asStateFlow()

    private val _dataListProductSearch = MutableStateFlow<List<Product>>(emptyList())
    val dataListProductSearch: StateFlow<List<Product>> = _dataListProductSearch.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getListProductsWithRetry(searchText:String, skip: Int,limit:Int , context: Context, status:Int) {
        try {
            getListProducts(searchText,skip,limit,context,status)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            //getListTasks(tokenUser,context)
        }
    }


    private fun getListProducts(searchText:String, skip: Int,limit:Int , context: Context, status:Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _productsState.value = GetStateList.Loading
            uiCheckStatus.value = StatusUI("Loading", "Loading")
            try {
                Log.d("getListTasks", "3")
                val response = when(status){
                    0 ->mainApi.getProducts(skip,limit)
                    1->mainApi.getProductsSearch(searchText)
                    2-> if(uiStateCurrentState.value.categories!="all") mainApi.getProductsByCategory(uiStateCurrentState.value.categories) else mainApi.getProducts(skip,limit)
                    else -> {mainApi.getProducts(skip,limit)}
                }

//                    if (onSearch) mainApi.getProductsSearch(searchText)  else mainApi.getProducts(skip,limit)
                if (response.isSuccessful) {
                    _isLoading.value = false
                    Log.d("getListTasks", "4")
                    uiCheckStatus.value = StatusUI("Success", "Success")
                    val products = response.body()
                    _productsState.value = products?.let { GetStateList.Success(it,status) }
                } else {
                    _isLoading.value = false
                    Toast.makeText(
                        context,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    _productsState.value = GetStateList.Error("Error")
                    uiCheckStatus.value = StatusUI("Error", "Error")

                }
            } catch (e: Exception) {
                _productsState.value = GetStateList.Error(e.message ?: "Error occurred")
                Toast.makeText(
                    context,
                    e.message ?: "Error occurred",
                    Toast.LENGTH_SHORT
                ).show()
                _isLoading.value = false
            }
        }
    }

    fun typeError(code: String) {
        uiCheckStatus.value = StatusUI("Error", code)
    }

    fun nullStatus() {
        uiCheckStatus.value = StatusUI()
    }
    fun setDataList(state: GetStateList.Success){
        val products= state.products.products
        when(state.status){
            0,2 -> _dataListProduct.value = products
            1 ->_dataListProductSearch.value=products
        }
    }

    fun onNewValueCategories(newValueCategories: String, newValuePage:Int) {
        uiStateCurrentState.value = uiStateCurrentState.value.copy(categories  = newValueCategories)
        uiStateCurrentState.value = uiStateCurrentState.value.copy(page  = newValuePage)
    }

}