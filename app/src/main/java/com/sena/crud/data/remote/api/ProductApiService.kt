package com.sena.crud.data.remote.api

import com.sena.crud.data.remote.dto.req.product.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products/{id}")
    suspend fun GetProductByid(
        @Path("id") id: Int
    ) : Product
}