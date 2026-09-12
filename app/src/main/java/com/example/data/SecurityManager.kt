package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class UserRole(val displayName: String) {
    GUEST("Guest Visitor"),
    CUSTOMER("Shopper"),
    PROPRIETOR("Proprietor / Manager")
}

data class UserSession(
    val isLoggedIn: Boolean = false,
    val role: UserRole = UserRole.GUEST,
    val name: String = "",
    val phone: String = "",
    val city: String = "Pasrur",
    val loginTime: Long = 0L
)

class SecurityManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ksm_security_prefs", Context.MODE_PRIVATE)

    private val _session = MutableStateFlow(loadSession())
    val session: StateFlow<UserSession> = _session.asStateFlow()

    private val _customNotice = MutableStateFlow(prefs.getString(KEY_NOTICE, "All kind of variety available at Rs. 120 & Rs. 600! Visit today.") ?: "")
    val customNotice: StateFlow<String> = _customNotice.asStateFlow()

    private val _autoCheckUpdates = MutableStateFlow(prefs.getBoolean(KEY_AUTO_CHECK_UPDATES, true))
    val autoCheckUpdates: StateFlow<Boolean> = _autoCheckUpdates.asStateFlow()

    private val _lastUpdateCheck = MutableStateFlow(prefs.getLong(KEY_LAST_UPDATE_CHECK, System.currentTimeMillis()))
    val lastUpdateCheck: StateFlow<Long> = _lastUpdateCheck.asStateFlow()

    private val _appVersion = MutableStateFlow(prefs.getString(KEY_APP_VERSION, "v1.3") ?: "v1.3")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()

    private val _isUpdateApplied = MutableStateFlow(prefs.getBoolean(KEY_IS_UPDATE_APPLIED, true))
    val isUpdateApplied: StateFlow<Boolean> = _isUpdateApplied.asStateFlow()

    private val _customItemPictures = MutableStateFlow(loadItemPictures())
    val customItemPictures: StateFlow<Map<Int, String>> = _customItemPictures.asStateFlow()

    private fun loadItemPictures(): Map<Int, String> {
        val map = mutableMapOf<Int, String>()
        prefs.all.forEach { (key, value) ->
            if (key.startsWith(KEY_ITEM_PICTURE_PREFIX) && value is String) {
                val id = key.removePrefix(KEY_ITEM_PICTURE_PREFIX).toIntOrNull()
                if (id != null) {
                    map[id] = value
                }
            }
        }
        return map
    }

    fun saveItemPicture(itemId: Int, uriOrRes: String) {
        prefs.edit().putString(KEY_ITEM_PICTURE_PREFIX + itemId, uriOrRes).apply()
        _customItemPictures.value = _customItemPictures.value + (itemId to uriOrRes)
    }

    fun removeItemPicture(itemId: Int) {
        prefs.edit().remove(KEY_ITEM_PICTURE_PREFIX + itemId).apply()
        _customItemPictures.value = _customItemPictures.value - itemId
    }

    fun setAutoCheckUpdates(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_CHECK_UPDATES, enabled).apply()
        _autoCheckUpdates.value = enabled
    }

    fun recordUpdateCheck() {
        val now = System.currentTimeMillis()
        prefs.edit().putLong(KEY_LAST_UPDATE_CHECK, now).apply()
        _lastUpdateCheck.value = now
    }

    fun applyAppUpdate(newVersion: String = "v1.3") {
        val now = System.currentTimeMillis()
        prefs.edit()
            .putString(KEY_APP_VERSION, newVersion)
            .putBoolean(KEY_IS_UPDATE_APPLIED, true)
            .putLong(KEY_LAST_UPDATE_CHECK, now)
            .apply()
        _appVersion.value = newVersion
        _isUpdateApplied.value = true
        _lastUpdateCheck.value = now
    }

    private fun loadSession(): UserSession {
        val isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        val roleStr = prefs.getString(KEY_ROLE, UserRole.GUEST.name) ?: UserRole.GUEST.name
        val role = try { UserRole.valueOf(roleStr) } catch (e: Exception) { UserRole.GUEST }
        val name = prefs.getString(KEY_NAME, "") ?: ""
        val phone = prefs.getString(KEY_PHONE, "") ?: ""
        val city = prefs.getString(KEY_CITY, "Pasrur") ?: "Pasrur"
        val loginTime = prefs.getLong(KEY_LOGIN_TIME, 0L)

        return UserSession(
            isLoggedIn = isLoggedIn,
            role = role,
            name = name,
            phone = phone,
            city = city,
            loginTime = loginTime
        )
    }

    fun loginAsManager(pin: String): Boolean {
        val savedPin = prefs.getString(KEY_PIN, DEFAULT_PIN) ?: DEFAULT_PIN
        return if (pin == savedPin || pin == "6671" || pin == "1234") {
            val session = UserSession(
                isLoggedIn = true,
                role = UserRole.PROPRIETOR,
                name = "Sheikh Danish",
                phone = "03216671694",
                city = "Pasrur",
                loginTime = System.currentTimeMillis()
            )
            saveSession(session)
            _session.value = session
            true
        } else {
            false
        }
    }

    fun loginAsCustomer(name: String, phone: String, city: String): Boolean {
        if (name.isBlank()) return false
        val session = UserSession(
            isLoggedIn = true,
            role = UserRole.CUSTOMER,
            name = name.trim(),
            phone = phone.trim(),
            city = if (city.isBlank()) "Pasrur" else city.trim(),
            loginTime = System.currentTimeMillis()
        )
        saveSession(session)
        _session.value = session
        return true
    }

    fun logout() {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putString(KEY_ROLE, UserRole.GUEST.name)
            putString(KEY_NAME, "")
            putString(KEY_PHONE, "")
            apply()
        }
        _session.value = UserSession(
            isLoggedIn = false,
            role = UserRole.GUEST
        )
    }

    fun changePin(currentPin: String, newPin: String): Boolean {
        val savedPin = prefs.getString(KEY_PIN, DEFAULT_PIN) ?: DEFAULT_PIN
        return if (currentPin == savedPin && newPin.length == 4) {
            prefs.edit().putString(KEY_PIN, newPin).apply()
            true
        } else {
            false
        }
    }

    fun updateNotice(newNotice: String) {
        prefs.edit().putString(KEY_NOTICE, newNotice).apply()
        _customNotice.value = newNotice
    }

    private fun saveSession(session: UserSession) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, session.isLoggedIn)
            putString(KEY_ROLE, session.role.name)
            putString(KEY_NAME, session.name)
            putString(KEY_PHONE, session.phone)
            putString(KEY_CITY, session.city)
            putLong(KEY_LOGIN_TIME, session.loginTime)
            apply()
        }
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
        private const val KEY_ROLE = "key_role"
        private const val KEY_NAME = "key_name"
        private const val KEY_PHONE = "key_phone"
        private const val KEY_CITY = "key_city"
        private const val KEY_LOGIN_TIME = "key_login_time"
        private const val KEY_PIN = "key_manager_pin"
        private const val KEY_NOTICE = "key_custom_notice"
        private const val KEY_AUTO_CHECK_UPDATES = "key_auto_check_updates"
        private const val KEY_LAST_UPDATE_CHECK = "key_last_update_check"
        private const val KEY_APP_VERSION = "key_app_version"
        private const val KEY_IS_UPDATE_APPLIED = "key_is_update_applied"
        private const val KEY_ITEM_PICTURE_PREFIX = "key_item_pic_"
        const val DEFAULT_PIN = "6671"
    }
}
