package com.zerocoders.moviestack

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    private object KeyName {
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
        const val USER_NAME = "USER_NAME"
    }

    val IS_LOGGED_IN = booleanPreferencesKey(KeyName.IS_LOGGED_IN)
    val USER_NAME = stringPreferencesKey(KeyName.USER_NAME)
}