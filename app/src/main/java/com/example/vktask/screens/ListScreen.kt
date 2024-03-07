package com.example.vktask.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.vktask.data.Products
import com.example.vktask.retrofit.GetStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context,
    listViewModel: ListViewModel= hiltViewModel()
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val isLoading by listViewModel.isLoading.collectAsState()

    //val dataList: List<Products> by listViewModel.dataListProduct.collectAsState()

    listViewModel.productsState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateList.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateList.Success -> {

                CoroutineScope(Job()).launch {
                    //mainViewModel.handleSuccessStateMaterialScreen(state)

                }
            }

            is GetStateList.Error -> {
                //mainViewModel.handleErrorStateMaterialsScreen(state)
                val error = state.error
                listViewModel.typeError(error)
            }
        }
    }
}