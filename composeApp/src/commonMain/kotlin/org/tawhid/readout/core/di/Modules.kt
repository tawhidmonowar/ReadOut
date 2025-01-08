package org.tawhid.readout.core.di

import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.readout.app.setting.SettingViewModel
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSourceImpl
import org.tawhid.readout.book.audiobook.data.repository.AudioBookRepositoryImpl
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.book.audiobook.presentation.SharedAudioBookViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeViewModel
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSourceImpl
import org.tawhid.readout.book.openbook.data.repository.BookRepositoryImpl
import org.tawhid.readout.book.openbook.domain.BookRepository
import org.tawhid.readout.book.openbook.presentation.SharedBookViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeViewModel
import org.tawhid.readout.core.data.network.HttpClientFactory
import org.tawhid.readout.core.player.data.repository.PlayerRepositoryImpl
import org.tawhid.readout.core.player.domain.PlayerRepository
import org.tawhid.readout.core.player.presentation.PlayerViewModel
import org.tawhid.readout.core.utils.AppPreferences

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    singleOf(::AppPreferences)
    viewModelOf(::SettingViewModel)

    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    single { PlayerViewModel(get()) }

    singleOf(::RemoteBookDataSourceImpl).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    viewModelOf(::BookHomeViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SharedBookViewModel)

    singleOf(::RemoteAudioBookDataSourceImpl).bind<RemoteAudioBookDataSource>()
    singleOf(::AudioBookRepositoryImpl).bind<AudioBookRepository>()
    viewModelOf(::AudioBookHomeViewModel)
    viewModelOf(::AudioBookDetailViewModel)
    viewModelOf(::SharedAudioBookViewModel)

}