package org.tawhid.readout.core.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.tawhid.readout.core.setting.SettingViewModel
import org.tawhid.readout.core.utils.AppPreferences

expect val platformModule: Module

val sharedModule = module {
    singleOf(::AppPreferences)
    viewModelOf(::SettingViewModel)
}