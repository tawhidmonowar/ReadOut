package org.tawhid.readout.core.data.datastore

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.mp.KoinPlatform
import org.tawhid.readout.core.utils.DATA_STORE_FILE_NAME

actual fun dataStorePreferences(): DataStore<Preferences> {
    val appContext = KoinPlatform.getKoin().get<Application>()
    return AppSettings.getDataStore(
        producePath = {
            appContext.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
    )
}