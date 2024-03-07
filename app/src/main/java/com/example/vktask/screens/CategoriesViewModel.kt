package com.example.vktask.screens

import androidx.lifecycle.ViewModel
import com.example.vktask.data.Product
import com.example.vktask.retrofit.AppRetrofit
import com.example.vktask.retrofit.GetDataApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel@Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    private val _dataListCategories = MutableStateFlow<List<Product>>(emptyList())
    val dataListCategories: StateFlow<List<Product>> = _dataListCategories.asStateFlow()
}