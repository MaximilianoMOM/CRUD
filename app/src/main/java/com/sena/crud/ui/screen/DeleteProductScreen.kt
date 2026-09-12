package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.ui.component.productCard
import com.sena.crud.ui.viewModel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteProductScreen(
    productId: Int,
    onNavigateToDelete: (Int) -> Unit,
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
                title = { Text("🗑️ Eliminar Producto (ID: $productId)") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = idInput,
                    onValueChange = { idInput = it },
                    label = { Text("ID de Producto a Eliminar") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        val newId = idInput.toIntOrNull() ?: 1
                        onNavigateToDelete(newId)
                    },
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text("Buscar")
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }
                    uiState.product != null -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (uiState.deleteSuccessMessage == null) {
                                Text(
                                    text = "¿Estás seguro de eliminar este producto?",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.error
                                )

                                productCard(product = uiState.product!!)

                                Button(
                                    onClick = { viewModel.deleteProduct(productId) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    enabled = !uiState.isDeleting
                                ) {
                                    if (uiState.isDeleting) {
                                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onError)
                                    } else {
                                        Text("Eliminar Producto", style = MaterialTheme.typography.titleMedium)
                                    }
                                }
                            }

                            if (uiState.deleteSuccessMessage != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = uiState.deleteSuccessMessage ?: "",
                                        modifier = Modifier.padding(12.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }
                    }
                    uiState.errorMessage != null -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = uiState.errorMessage ?: "Error", color = MaterialTheme.colorScheme.error)
                            Button(onClick = { viewModel.getProductById(productId) }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
            }
        }
    }
}
