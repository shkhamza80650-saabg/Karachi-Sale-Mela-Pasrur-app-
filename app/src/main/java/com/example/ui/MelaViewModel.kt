package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppSection(val label: String) {
    HOME("Home"),
    VARIETIES("Our Varieties"),
    DAILY_ARRIVALS("Daily Arrivals"),
    GALLERY("Shop Gallery"),
    VISIT_SHOP("Visit Our Shop"),
    PROPRIETOR("Proprietor"),
    CONTACT("Contact WhatsApp"),
    ABOUT("About Us")
}

class MelaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MelaRepository

    val currentSection = MutableStateFlow(AppSection.HOME)
    val varietySearchQuery = MutableStateFlow("")
    val selectedVarietyCategory = MutableStateFlow("All Items")
    val showAddArrivalDialog = MutableStateFlow(false)

    val allArrivals: StateFlow<List<DailyArrivalEntity>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MelaRepository(database.dailyArrivalDao())

        viewModelScope.launch {
            repository.seedInitialArrivalsIfEmpty()
        }

        allArrivals = repository.allArrivals.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    val filteredVarieties: StateFlow<List<VarietyItem>> = combine(
        varietySearchQuery,
        selectedVarietyCategory
    ) { query, category ->
        VarietiesData.allVarieties.filter { item ->
            val matchesCategory = (category == "All Items") || (item.category == category)
            val matchesQuery = query.isBlank() ||
                    item.name.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true) ||
                    item.priceTier.contains(query, ignoreCase = true) ||
                    item.description.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VarietiesData.allVarieties
    )

    fun navigateTo(section: AppSection) {
        currentSection.value = section
    }

    fun onSearchQueryChanged(query: String) {
        varietySearchQuery.value = query
    }

    fun onCategorySelected(category: String) {
        selectedVarietyCategory.value = category
    }

    fun setAddArrivalDialogVisible(visible: Boolean) {
        showAddArrivalDialog.value = visible
    }

    fun addNewArrival(
        title: String,
        category: String,
        priceTier: String,
        arrivalDate: String,
        description: String
    ) {
        viewModelScope.launch {
            val arrival = DailyArrivalEntity(
                title = title,
                category = category,
                priceTier = priceTier,
                arrivalDate = arrivalDate.ifBlank { "Today's Arrival" },
                description = description,
                imageResName = "img_shop_interior",
                timestamp = System.currentTimeMillis()
            )
            repository.addArrival(arrival)
            showAddArrivalDialog.value = false
        }
    }

    fun removeArrival(id: Int) {
        viewModelScope.launch {
            repository.removeArrival(id)
        }
    }
}
