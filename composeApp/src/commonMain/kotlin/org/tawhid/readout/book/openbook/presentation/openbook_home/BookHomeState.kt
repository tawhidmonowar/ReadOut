package org.tawhid.readout.book.openbook.presentation.openbook_home

import org.tawhid.readout.book.openbook.domain.Book
import org.tawhid.readout.core.utils.UiText

data class BookHomeState(
    val searchQuery: String = "",
    val searchResult: List<Book> = emptyList(),

    val isSearchActive: Boolean = false,
    val isSearchLoading: Boolean = false,
    val searchErrorMsg: UiText? = null,

    val trendingBooks: List<Book> = emptyList(),
    val isTrendingLoading: Boolean = false,
    val trendingErrorMsg: UiText? = null,

    val browseBooks: List<Book> = emptyList(),
    val isBrowseLoading: Boolean = false,
    val browseErrorMsg: UiText? = null,
    val endReached: Boolean = false,
    val isBrowseShimmerEffectVisible: Boolean = true,

    val savedBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: UiText? = null
)