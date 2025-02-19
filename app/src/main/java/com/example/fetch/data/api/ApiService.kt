package com.example.fetch.data.api

import com.example.fetch.data.vo.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}