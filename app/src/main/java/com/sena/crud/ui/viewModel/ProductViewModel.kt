package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.useCase.CreateProductUseCase
import com.sena.crud.domain.useCase.DeleteProductUseCase
import com.sena.crud.domain.useCase.GetAllProductsUseCase
import com.sena.crud.domain.useCase.GetProductUseCase
import com.sena.crud.domain.useCase.UpdateProductUseCase
import com.sena.crud.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    fun getAllProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val list = getAllProductsUseCase()
                _uiState.update { it.copy(isLoading = false, products = list, errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message ?: "Error al obtener productos") }
            }
        }
    }

    fun getProductById(id: Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    updateSuccessMessage = null,
                    deleteSuccessMessage = null,
                    createSuccessMessage = null
                )
            }
            try {
                val result = getProductUseCase(id)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        product = result,
                        errorMessage = null
                    )
                }

            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        product = null,
                        errorMessage = e.message?: "Error al cargar el producto"
                    )
                }
            }
        }
    }

    fun createProduct(title: String, description: String, category: String, price: Double) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isCreating = true,
                    createSuccessMessage = null,
                    errorMessage = null
                )
            }
            try {
                val newProduct = createProductUseCase(title, description, category, price)
                _uiState.update {
                    it.copy(
                        isCreating = false,
                        product = newProduct,
                        createSuccessMessage = "¡Producto creado exitosamente (ID: ${newProduct.id})!"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isCreating = false,
                        errorMessage = e.message ?: "Error al crear el producto"
                    )
                }
            }
        }
    }

    fun updateProduct(id: Int, title: String, price: Double) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUpdating = true,
                    updateSuccessMessage = null,
                    errorMessage = null
                )
            }
            try {
                val updatedProduct = updateProductUseCase(id, title, price)
                _uiState.update {
                    it.copy(
                        isUpdating = false,
                        product = updatedProduct,
                        updateSuccessMessage = "¡Producto actualizado exitosamente!"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isUpdating = false,
                        errorMessage = e.message ?: "Error al actualizar el producto"
                    )
                }
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isDeleting = true,
                    deleteSuccessMessage = null,
                    errorMessage = null
                )
            }
            try {
                val success = deleteProductUseCase(id)
                if (success) {
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            deleteSuccessMessage = "¡Producto eliminado exitosamente!"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            errorMessage = "No se pudo eliminar el producto"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isDeleting = false,
                        deleteSuccessMessage = "¡Producto eliminado exitosamente!" // dummyjson simulated delete
                    )
                }
            }
        }
    }
}
