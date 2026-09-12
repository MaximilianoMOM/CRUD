package com.sena.crud.ui.state

import com.sena.crud.domain.model.ProductModel

data class ProductUIState(
    val isLoading: Boolean = false,
    val product: ProductModel? = null,
    val products: List<ProductModel> = emptyList(),
    val errorMessage: String? = null,
    val isUpdating: Boolean = false,
    val updateSuccessMessage: String? = null,
    val isDeleting: Boolean = false,
    val deleteSuccessMessage: String? = null,
    val isCreating: Boolean = false,
    val createSuccessMessage: String? = null
)
