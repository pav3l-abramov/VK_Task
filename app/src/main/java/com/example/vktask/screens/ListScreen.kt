package com.example.vktask.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.vktask.OPEN_ITEM_SCREEN
import com.example.vktask.common.composable.CategoriesDialog
import com.example.vktask.common.composable.CustomLinearProgressBar
import com.example.vktask.common.composable.FABTaskComposable
import com.example.vktask.common.composable.ProductCard
import com.example.vktask.common.ext.fieldModifier
import com.example.vktask.retrofit.GetStateCategories
import com.example.vktask.retrofit.GetStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context,
    listViewModel: ListViewModel= hiltViewModel(),
    categoriesViewModel: CategoriesViewModel= hiltViewModel(),
    viewModel : SharedViewModel
) {
    val isActiveSearch = remember { mutableStateOf(false) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val isLoading by listViewModel.isLoading.collectAsState()
    val dataList by listViewModel.dataListProduct.collectAsState()
    val categoriesList by categoriesViewModel.dataListCategories.collectAsState()
    val dataListSearch by listViewModel.dataListProductSearch.collectAsState()
    val uiStateCurrentState by listViewModel.uiStateCurrentState
    val isDialogOpen = remember { mutableStateOf(false) }
    val isMainFABOpen = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            if (dataList.isEmpty()) {
                listViewModel.getListProductsWithRetry(
                    "",
                    0, 20,
                    context,
                    0
                )
            }
        }
        if (categoriesList.isEmpty()) {
            categoriesViewModel.getListCategoriesWithRetry(
                context
            )
        }

    }

    Scaffold(
        floatingActionButton = {
            FABTaskComposable(
                onCancelMain = { isMainFABOpen.value=!isMainFABOpen.value },
                onCancelPageUp = listViewModel::onNewValueCategories ,
                onCancelPageDown = listViewModel::onNewValueCategories,
                onButton= { listViewModel.getListProductsWithRetry("",(uiStateCurrentState.page-1)*20,20,context,0) },
                onCancelCategories = { isDialogOpen.value =true },
                mainButtonOn = isMainFABOpen.value,
                currentPage = uiStateCurrentState.page,
                color = MaterialTheme.colorScheme.background,
                pageIsEmpty = dataList.isNotEmpty()
            )
        },
        content = {

            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val searchText = remember { mutableStateOf("") }
                SearchBar(
                    modifier = Modifier.fieldModifier(),
                    query = searchText.value,
                    onQueryChange = { text ->
                        searchText.value = text
                    },
                    onSearch = {
                        Log.d("searchText.value", searchText.value)
                        listViewModel.getListProductsWithRetry(
                            searchText.value,
                            0, 20,
                            context,
                            1
                        )

                    },
                    placeholder = {
                        Text(text = "Search...")
                    },
                    active = isActiveSearch.value,
                    onActiveChange = {
                        isActiveSearch.value = it
                    }) {
                    LazyColumn {
                        items(dataListSearch) { item ->
                            ProductCard(
                                content = item.title,
                                imgUrl = item.thumbnail,
                                modifier = Modifier.fieldModifier(),
                                price = item.price,
                                description = item.description
                            ) {
                                viewModel.setData(item)
                                navController.navigate(route = OPEN_ITEM_SCREEN)
                            }

                        }
                    }
                }

                if (isLoading) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomLinearProgressBar(Modifier.fieldModifier())
                    }

                }

                LazyColumn {
                    items(dataList) { item ->
                        ProductCard(
                            content = item.title,
                            imgUrl = item.thumbnail,
                            description = item.description,
                            modifier = Modifier.fieldModifier(),
                            price = item.price
                        ) {
                            viewModel.setData(item)
                            navController.navigate(route = OPEN_ITEM_SCREEN)
                        }
                    }
                }
            }
        })

    if (isDialogOpen.value) {
        CategoriesDialog(
            message = "Choose category",
            status = uiStateCurrentState.categories,
            listStatus = categoriesList,
            onNewValueCategories=listViewModel::onNewValueCategories,
            onOkButton= { listViewModel.getListProductsWithRetry("",0,20,context,2) },
            modifier = Modifier.fieldModifier(),
            onCancel = { isDialogOpen.value = false },
            color = MaterialTheme.colorScheme.background
        )
    }




    listViewModel.productsState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateList.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateList.Success -> {
                CoroutineScope(Job()).launch {
                    listViewModel.setDataList(state)
                }
            }

            is GetStateList.Error -> {
                val error = state.error
                listViewModel.typeError(error)
            }
        }
    }

    categoriesViewModel.categoriesState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateCategories.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateCategories.Success -> {
                CoroutineScope(Job()).launch {
                    categoriesViewModel.setDataList(state)
                }
            }

            is GetStateCategories.Error -> {
                val error = state.error
                categoriesViewModel.typeError(error)
            }
        }
    }
}