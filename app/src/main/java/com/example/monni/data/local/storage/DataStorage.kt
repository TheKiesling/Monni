package com.example.monni.data.local.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStorage(val context: Context) {
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
    }

    suspend fun getValueFromKey(key:String):String?{
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun saveKeyValue(key:String, value:String){
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit{ settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun removeKey(key:String){
        val dataStoredKey = stringPreferencesKey(key)
        context.dataStore.edit{ settings ->
            settings.remove(dataStoredKey)
        }
    }
}