package org.tawhid.readout.core.di

import org.koin.core.module.dsl.singleOf
import org.tawhid.readout.core.data.datastore.dataStorePreferences
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::dataStorePreferences)
}