package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.ui.section.ProductDetails
import com.sena.crud.ui.viewModel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var idInput by remember(productId) { mutableStateOf(productId.toString()) }

    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🔍 Buscar por ID (Actual: $productId)") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = idInput,
                    onValueChange = { idInput = it },
                    label = { Text("ID de Producto") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        val newId = idInput.toIntOrNull() ?: 1
                        onNavigateToDetail(newId)
                    },
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text("Buscar")
                }
            }

            ProductDetails(
                uiState = uiState,
                onRetry = { viewModel.getProductById(productId) },
                onUpdate = { _, _ -> },
                isReadOnly = true
            )
        }
    }
}
