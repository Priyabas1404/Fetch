package com.example.fetch.data.repository

import com.example.fetch.data.api.ApiService
import com.example.fetch.data.vo.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getItems(): List<Item> {
        return try {
            apiService.getItems()
        } catch (e: Exception) {
            emptyList()
        }
    }
}