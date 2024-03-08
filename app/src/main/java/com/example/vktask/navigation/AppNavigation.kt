package com.example.vktask.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vktask.OPEN_ITEM_SCREEN
import com.example.vktask.screens.HomeScreen
import com.example.vktask.screens.ListScreen
import com.example.vktask.screens.OpenItemScreen
import com.example.vktask.screens.SharedViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val viewModel: SharedViewModel = hiltViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            if (currentDestination != null) {
                if (currentDestination.route?.contains(
                        OPEN_ITEM_SCREEN,
                        ignoreCase = true
                    ) == false
                ) {
                    NavigationBar {
                        screens.forEach { navItem ->
                            NavigationBarItem(selected = currentDestination.hierarchy.any { it.route == navItem.route },
                                onClick = {
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = navItem.icon),
                                        contentDescription = null
                                    )
                                },
                                label = { Text(text = navItem.title) }
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = BottomBarScreen.Home.route) {
                HomeScreen( context = context)

            }

            composable(route = BottomBarScreen.List.route) {
                EnterAnimation { ListScreen(navController = navController, context = context,viewModel=viewModel) }
            }
            composable(route = OPEN_ITEM_SCREEN) {
                EnterAnimation { OpenItemScreen(navController = navController,viewModel=viewModel) }
            }

        }

    }

}

@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -1 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.0f),
        exit = slideOutVertically() + fadeOut(),
    ) {
        content()
    }
}