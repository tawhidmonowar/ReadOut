package org.tawhid.readout.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.tawhid.readout.core.data.datastore.dataStorePreferences

actual val platformModule = module {
    singleOf(::dataStorePreferences)
}