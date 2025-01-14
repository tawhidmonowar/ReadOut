package org.tawhid.readout.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.tawhid.readout.app.home.data.network.RemoteHomeDataSource
import org.tawhid.readout.app.home.data.network.RemoteHomeDataSourceImpl
import org.tawhid.readout.app.home.data.repository.HomeRepositoryImpl
import org.tawhid.readout.app.home.domain.repository.HomeRepository
import org.tawhid.readout.app.home.domain.usecase.GetRecentReleasedAudioBooksUseCase
import org.tawhid.readout.app.home.domain.usecase.GetSavedRecentReleaseBooksUseCase
import org.tawhid.readout.app.home.domain.usecase.GetTrendingBooksUseCase
import org.tawhid.readout.app.home.presentation.HomeViewModel
import org.tawhid.readout.app.home.presentation.recentrelease.RecentReleaseViewModel
import org.tawhid.readout.app.setting.SettingViewModel
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSource
import org.tawhid.readout.book.audiobook.data.network.RemoteAudioBookDataSourceImpl
import org.tawhid.readout.book.audiobook.data.repository.AudioBookRepositoryImpl
import org.tawhid.readout.book.audiobook.domain.repository.AudioBookRepository
import org.tawhid.readout.book.audiobook.domain.usecase.DeleteFromSavedUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetAudioBookSummaryUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetAudioBookTracksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetBookByIdUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetBrowseAudioBooksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.GetSavedAudioBooksUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.SaveAudioBookUseCase
import org.tawhid.readout.book.audiobook.domain.usecase.SearchAudioBooksUseCase
import org.tawhid.readout.book.audiobook.presentation.SharedAudioBookViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_detail.AudioBookDetailViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_home.AudioBookHomeViewModel
import org.tawhid.readout.book.audiobook.presentation.audiobook_saved.AudioBookSavedViewModel
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSource
import org.tawhid.readout.book.openbook.data.network.RemoteBookDataSourceImpl
import org.tawhid.readout.book.openbook.data.repository.BookRepositoryImpl
import org.tawhid.readout.book.openbook.domain.repository.BookRepository
import org.tawhid.readout.book.openbook.domain.usecase.DeleteBookFromSavedUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetBookDescriptionUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetBrowseBooksUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetOpenBookByIdUseCase
import org.tawhid.readout.book.openbook.domain.usecase.GetSaveBooksUseCase
import org.tawhid.readout.book.openbook.domain.usecase.SaveBookUseCase
import org.tawhid.readout.book.openbook.domain.usecase.SearchBooksUseCase
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
    singleOf(::GetOpenBookByIdUseCase)
    singleOf(::SaveBookUseCase)
    singleOf(::DeleteBookFromSavedUseCase)
    singleOf(::GetSaveBooksUseCase)
    singleOf(::GetBookDescriptionUseCase)
    singleOf(::GetBrowseBooksUseCase)
    singleOf(::SearchBooksUseCase)

    singleOf(::RemoteAudioBookDataSourceImpl).bind<RemoteAudioBookDataSource>()
    singleOf(::AudioBookRepositoryImpl).bind<AudioBookRepository>()
    viewModelOf(::AudioBookHomeViewModel)
    viewModelOf(::AudioBookDetailViewModel)
    viewModelOf(::AudioBookSavedViewModel)
    viewModelOf(::SharedAudioBookViewModel)
    singleOf(::GetAudioBookTracksUseCase)
    singleOf(::SaveAudioBookUseCase)
    singleOf(::GetBookByIdUseCase)
    singleOf(::DeleteFromSavedUseCase)
    singleOf(::GetAudioBookSummaryUseCase)
    singleOf(::GetSavedAudioBooksUseCase)
    singleOf(::SearchAudioBooksUseCase)
    singleOf(::GetBrowseAudioBooksUseCase)

    singleOf(::RemoteSummarizeDataSourceImpl).bind<RemoteSummarizeDataSource>()
    singleOf(::SummarizeRepositoryImpl).bind<SummarizeRepository>()
    singleOf(::GetBookSummaryUseCase)
    viewModelOf(::SummarizeViewModel)

    singleOf(::RemoteHomeDataSourceImpl).bind<RemoteHomeDataSource>()
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    singleOf(::GetRecentReleasedAudioBooksUseCase)
    singleOf(::GetSavedRecentReleaseBooksUseCase)
    singleOf(::GetTrendingBooksUseCase)
    viewModelOf(::HomeViewModel)
    viewModelOf(::RecentReleaseViewModel)

}