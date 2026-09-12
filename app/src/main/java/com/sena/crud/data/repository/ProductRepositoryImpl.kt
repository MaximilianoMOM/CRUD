package com.sena.crud.data.repository

import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.remote.api.ProductApiService
import com.sena.crud.data.remote.dto.req.product.ProductCreateReq
import com.sena.crud.data.remote.dto.req.product.ProductUpdateReq
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import jakarta.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService
): ProductRepository {
    // Cache in-memory para productos eliminados (simulación para dummyjson)
    private val deletedProductIds = mutableSetOf<Int>()

    override suspend fun getAllProducts(): List<ProductModel> {
        val response = api.getAllProducts()
        return response.products
            .map { it.toDomain() }
            .filterNot { deletedProductIds.contains(it.id) }
    }

    override suspend fun GetProductById(id: Int): ProductModel {
        val response = api.GetProductByid(id)
        return response.toDomain()
    }

    override suspend fun createProduct(title: String, description: String, category: String, price: Double): ProductModel {
        val request = ProductCreateReq(title = title, description = description, category = category, price = price)
        val response = api.createProduct(request)
        return response.toDomain()
    }

    override suspend fun updateProduct(id: Int, title: String, price: Double): ProductModel {
        val request = ProductUpdateReq(title = title, price = price)
        val response = api.updateProduct(id, request)
        return response.toDomain()
    }

    override suspend fun deleteProduct(id: Int): Boolean {
        return try {
            api.deleteProduct(id)
            deletedProductIds.add(id)
            true
        } catch (e: Exception) {
            deletedProductIds.add(id)
            true // Consider success on simulated delete response
        }
    }
}
