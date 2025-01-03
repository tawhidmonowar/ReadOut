package org.tawhid.readout.core.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSourceImpl
import org.tawhid.readout.book.openbook.data.repository.BookRepositoryImpl
import org.tawhid.readout.book.openbook.domain.BookRepository
import org.tawhid.readout.book.openbook.presentation.SharedBookViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeViewModel
import org.tawhid.readout.core.data.network.HttpClientFactory
import org.tawhid.readout.core.setting.SettingViewModel
import org.tawhid.readout.core.utils.AppPreferences

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::AppPreferences)
    singleOf(::RemoteBookDataSourceImpl).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()

    viewModelOf(::SettingViewModel)
    viewModelOf(::BookHomeViewModel)
    viewModelOf(::SharedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}