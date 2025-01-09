package org.tawhid.readout.core.di

import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.tawhid.readout.core.data.database.DatabaseFactory
import org.tawhid.readout.core.data.datastore.dataStorePreferences
import org.tawhid.readout.core.player.PlayerController

actual val platformModule = module {
    single { DatabaseFactory(androidApplication()) }
    singleOf(::dataStorePreferences)
    singleOf(::PlayerController)
}