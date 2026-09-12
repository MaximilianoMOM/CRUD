package com.sena.crud.domain.repository

import com.sena.crud.domain.model.ProductModel

interface ProductRepository {
    suspend fun getAllProducts(): List<ProductModel>

    suspend fun GetProductById(
        id: Int
    ): ProductModel

    suspend fun createProduct(
        title: String,
        description: String,
        category: String,
        price: Double
    ): ProductModel

    suspend fun updateProduct(
        id: Int,
        title: String,
        price: Double
    ): ProductModel

    suspend fun deleteProduct(
        id: Int
    ): Boolean
}
