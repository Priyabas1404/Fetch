import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.data.api.ApiClient
import com.example.fetch.data.vo.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val _items = MutableStateFlow<Map<Int, List<Item>>>(emptyMap())
    val items: StateFlow<Map<Int, List<Item>>> get() = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                val rawItems = ApiClient.apiService.getItems()

                // Filter out items with null or blank name
                val filteredItems = rawItems.filter { it.name != null }

                // Sort by listId and name
                val sortedItems = filteredItems.sortedWith(compareBy({ it.listId }, { it.name }))

                // Group by listId
                val groupedItems = sortedItems.groupBy { it.listId }

                _items.value = groupedItems
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
