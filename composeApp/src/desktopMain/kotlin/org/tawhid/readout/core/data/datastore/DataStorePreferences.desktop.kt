package org.tawhid.readout.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.tawhid.readout.core.utils.DATA_STORE_FILE_NAME

actual fun dataStorePreferences(): DataStore<Preferences> {
    return AppSettings.getDataStore {
        DATA_STORE_FILE_NAME
    }
}