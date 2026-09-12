package com.sena.crud.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sena.crud.ui.component.CrudNavBar
import com.sena.crud.ui.screen.CreateProductScreen
import com.sena.crud.ui.screen.DeleteProductScreen
import com.sena.crud.ui.screen.ProductDetailScreen
import com.sena.crud.ui.screen.ProductListScreen
import com.sena.crud.ui.screen.ProductScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "detail/1"

    // Evitar ID 0 o nulo al navegar desde pantallas que no tienen {id} como la lista
    val currentId = navBackStackEntry?.arguments?.getInt("id")?.takeIf { it > 0 } ?: 1

    Scaffold(
        bottomBar = {
            CrudNavBar(
                currentRoute = currentRoute,
                onNavigateToDetail = { navController.navigate("detail/$currentId") { launchSingleTop = true } },
                onNavigateToList = { navController.navigate("list") { launchSingleTop = true } },
                onNavigateToUpdate = { navController.navigate("update/$currentId") { launchSingleTop = true } },
                onNavigateToDelete = { navController.navigate("delete/$currentId") { launchSingleTop = true } },
                onNavigateToCreate = { navController.navigate("create") { launchSingleTop = true } }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "detail/1",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("list") {
                ProductListScreen(
                    onProductClick = { id -> navController.navigate("detail/$id") }
                )
            }
            composable("create") {
                CreateProductScreen()
            }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")?.takeIf { it > 0 } ?: 1
                ProductDetailScreen(
                    productId = id,
                    onNavigateToDetail = { newId -> navController.navigate("detail/$newId") }
                )
            }
            composable(
                route = "update/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")?.takeIf { it > 0 } ?: 1
                ProductScreen(
                    productId = id,
                    onNavigateToUpdate = { newId -> navController.navigate("update/$newId") }
                )
            }
            composable(
                route = "delete/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")?.takeIf { it > 0 } ?: 1
                DeleteProductScreen(
                    productId = id,
                    onNavigateToDelete = { newId -> navController.navigate("delete/$newId") }
                )
            }
        }
    }
}
