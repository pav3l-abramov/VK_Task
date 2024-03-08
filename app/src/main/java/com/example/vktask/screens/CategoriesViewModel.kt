package com.example.vktask.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktask.data.Product
import com.example.vktask.retrofit.AppRetrofit
import com.example.vktask.retrofit.GetDataApi
import com.example.vktask.retrofit.GetStateCategories
import com.example.vktask.retrofit.GetStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel@Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    private val _dataListCategories = MutableStateFlow<List<String>>(emptyList())
    val dataListCategories: StateFlow<List<String>> = _dataListCategories.asStateFlow()

    private val _categoriesState = MutableLiveData<GetStateCategories>()
    val categoriesState: LiveData<GetStateCategories> = _categoriesState

    fun getListCategoriesWithRetry( context: Context) {
        try {
            getListCategories(context)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
        }
    }


    private fun getListCategories( context: Context) {
        viewModelScope.launch {
            _categoriesState.value = GetStateCategories.Loading
            try {
                Log.d("getListTasks", "3")
                val response =mainApi.getCategories()
                if (response.isSuccessful) {
                    val products = response.body()
                    _categoriesState.value = products?.let { GetStateCategories.Success(it) }
                } else {
                    Toast.makeText(
                        context,
                        "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    _categoriesState.value = GetStateCategories.Error("Error")

                }
            } catch (e: Exception) {
                _categoriesState.value = GetStateCategories.Error(e.message ?: "Error occurred")
                Toast.makeText(
                    context,
                    e.message ?: "Error occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun typeError(code: String) {

    }


    fun setDataList(state: GetStateCategories.Success){
        val categories= state.categories
        _dataListCategories.value=categories

    }
}