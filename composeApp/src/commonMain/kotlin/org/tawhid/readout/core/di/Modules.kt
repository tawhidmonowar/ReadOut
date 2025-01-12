package org.tawhid.readout.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.readout.app.home.data.repository.HomeRepositoryImpl
import org.tawhid.readout.app.home.domain.HomeRepository
import org.tawhid.readout.app.home.presentation.HomeViewModel
import org.tawhid.readout.app.setting.SettingViewModel
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSourceImpl
import org.tawhid.readout.book.audiobook.data.repository.AudioBookRepositoryImpl
import org.tawhid.readout.book.audiobook.domain.AudioBookRepository
import org.tawhid.readout.book.audiobook.presentation.SharedAudioBookViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_saved.AudioBookSavedViewModel
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSourceImpl
import org.tawhid.readout.book.openbook.data.repository.BookRepositoryImpl
import org.tawhid.readout.book.openbook.domain.BookRepository
import org.tawhid.readout.book.openbook.presentation.SharedBookViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_detail.BookDetailViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_home.BookHomeViewModel
import org.tawhid.readout.book.openbook.presentation.openbook_saved.BookSavedViewModel
import org.tawhid.readout.book.summarize.data.network.RemoteSummarizeDataSource
import org.tawhid.readout.book.summarize.data.network.RemoteSummarizeDataSourceImpl
import org.tawhid.readout.book.summarize.data.repository.SummarizeRepositoryImpl
import org.tawhid.readout.book.summarize.domain.repository.SummarizeRepository
import org.tawhid.readout.book.summarize.domain.usecase.GetBookSummaryUseCase
import org.tawhid.readout.book.summarize.presentation.summarize_home.SummarizeViewModel
import org.tawhid.readout.core.data.database.DatabaseFactory
import org.tawhid.readout.core.data.database.ReadOutDatabase
import org.tawhid.readout.core.data.network.HttpClientFactory
import org.tawhid.readout.core.player.data.repository.PlayerRepositoryImpl
import org.tawhid.readout.core.player.domain.PlayerRepository
import org.tawhid.readout.core.player.presentation.PlayerViewModel
import org.tawhid.readout.core.utils.AppPreferences

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(OkHttp.create()) }
    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }
    single { get<ReadOutDatabase>().openBookDao }
    single { get<ReadOutDatabase>().audioBookDao }
    single { get<ReadOutDatabase>().summarizeDao }

    viewModelOf(::SettingViewModel)
    viewModelOf(::HomeViewModel)
    singleOf(::AppPreferences)

    singleOf(::PlayerRepositoryImpl).bind<PlayerRepository>()
    single { PlayerViewModel(get()) }

    singleOf(::RemoteBookDataSourceImpl).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
    viewModelOf(::BookHomeViewModel)
    viewModelOf(::BookSavedViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SharedBookViewModel)

    singleOf(::RemoteAudioBookDataSourceImpl).bind<RemoteAudioBookDataSource>()
    singleOf(::AudioBookRepositoryImpl).bind<AudioBookRepository>()
    viewModelOf(::AudioBookHomeViewModel)
    viewModelOf(::AudioBookDetailViewModel)
    viewModelOf(::AudioBookSavedViewModel)
    viewModelOf(::SharedAudioBookViewModel)

    singleOf(::RemoteSummarizeDataSourceImpl).bind<RemoteSummarizeDataSource>()
    singleOf(::SummarizeRepositoryImpl).bind<SummarizeRepository>()
    singleOf(::GetBookSummaryUseCase)
    viewModelOf(::SummarizeViewModel)

    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    viewModelOf(::HomeViewModel)

}