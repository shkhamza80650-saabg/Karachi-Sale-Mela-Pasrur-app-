package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppSection(val label: String) {
    HOME("Home"),
    VARIETIES("Categories"),
    SEARCH("Search"),
    DAILY_ARRIVALS("Daily Arrivals"),
    GALLERY("Shop Gallery"),
    VISIT_SHOP("Visit Our Shop"),
    PROPRIETOR("Proprietor"),
    CONTACT("Contact"),
    ABOUT("About Us"),
    MORE("More")
}

class MelaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MelaRepository
    val securityManager = SecurityManager(application)

    val currentSection = MutableStateFlow(AppSection.HOME)
    val varietySearchQuery = MutableStateFlow("")
    val selectedVarietyCategory = MutableStateFlow("All Items")
    val showAddArrivalDialog = MutableStateFlow(false)

    val allArrivals: StateFlow<List<DailyArrivalEntity>>
    val session: StateFlow<UserSession> = securityManager.session
    val customNotice: StateFlow<String> = securityManager.customNotice
    val autoCheckUpdates: StateFlow<Boolean> = securityManager.autoCheckUpdates
    val lastUpdateCheck: StateFlow<Long> = securityManager.lastUpdateCheck
    val appVersion: StateFlow<String> = securityManager.appVersion
    val isUpdateApplied: StateFlow<Boolean> = securityManager.isUpdateApplied
    val customItemPictures: StateFlow<Map<Int, String>> = securityManager.customItemPictures

    fun updateItemPicture(itemId: Int, imageUriOrRes: String) {
        securityManager.saveItemPicture(itemId, imageUriOrRes)
    }

    fun removeItemPicture(itemId: Int) {
        securityManager.removeItemPicture(itemId)
    }

    fun setAutoCheckUpdates(enabled: Boolean) {
        securityManager.setAutoCheckUpdates(enabled)
    }

    fun recordUpdateCheck() {
        securityManager.recordUpdateCheck()
    }

    fun applyAppUpdate(newVersion: String = "v1.3") {
        securityManager.applyAppUpdate(newVersion)
    }

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

    fun loginAsManager(pin: String): Boolean {
        return securityManager.loginAsManager(pin)
    }

    fun loginAsCustomer(name: String, phone: String, city: String): Boolean {
        return securityManager.loginAsCustomer(name, phone, city)
    }

    fun logout() {
        securityManager.logout()
    }

    fun updateNotice(notice: String) {
        securityManager.updateNotice(notice)
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
        description: String,
        imageResName: String = "img_shop_interior"
    ) {
        viewModelScope.launch {
            val arrival = DailyArrivalEntity(
                title = title,
                category = category,
                priceTier = priceTier,
                arrivalDate = arrivalDate.ifBlank { "Today's Arrival" },
                description = description,
                imageResName = imageResName,
                timestamp = System.currentTimeMillis()
            )
            repository.addArrival(arrival)
            showAddArrivalDialog.value = false
        }
    }

    fun updateArrivalImage(id: Int, newImageUriOrRes: String) {
        viewModelScope.launch {
            repository.updateArrivalImage(id, newImageUriOrRes)
        }
    }

    fun removeArrival(id: Int) {
        viewModelScope.launch {
            repository.removeArrival(id)
        }
    }
}
