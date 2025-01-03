package org.tawhid.readout.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Setting : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object AudioBooks : Route

    @Serializable
    data object Summarize : Route


    @Serializable
    data object OpenLibraryGraph : Route

    @Serializable
    data object OpenLibrary : Route

    @Serializable
    data class OpenLibraryDetail(val id: String? = null) : Route

}