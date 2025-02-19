import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.data.repository.ItemRepository
import com.example.fetch.data.vo.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    private val _items = MutableStateFlow<Map<Int, List<Item>>>(emptyMap())
    val items: StateFlow<Map<Int, List<Item>>> get() = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                val rawItems = repository.getItems()

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
