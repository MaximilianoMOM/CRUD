package com.sena.crud.data.remote.api

import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.data.remote.dto.req.product.ProductCreateReq
import com.sena.crud.data.remote.dto.req.product.ProductUpdateReq
import com.sena.crud.data.remote.dto.req.product.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getAllProducts(): ProductsResponse

    @GET("products/{id}")
    suspend fun GetProductByid(
        @Path("id") id: Int
    ) : Product

    @POST("products/add")
    suspend fun createProduct(
        @Body body: ProductCreateReq
    ): Product

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body body: ProductUpdateReq
    ): Product

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Product
}
