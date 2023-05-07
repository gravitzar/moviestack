package com.zerocoders.moviestack

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.zerocoders.moviestack.utils.sessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SessionPreferenceManager(private val context: Context) {

    val isLoggedInFlow: Flow<Boolean> = context.sessionDataStore.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[PreferenceKeys.IS_LOGGED_IN] ?: false
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) = context.sessionDataStore.edit { pref ->
        pref[PreferenceKeys.IS_LOGGED_IN] = isLoggedIn
    }

    val userNameFlow: Flow<String> = context.sessionDataStore.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[PreferenceKeys.USER_NAME].orEmpty()
    }

    suspend fun saveUserName(userName: String) = context.sessionDataStore.edit { pref ->
        pref[PreferenceKeys.USER_NAME] = userName
    }
}