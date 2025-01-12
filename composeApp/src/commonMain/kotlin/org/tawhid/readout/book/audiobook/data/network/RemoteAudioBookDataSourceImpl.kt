package org.tawhid.readout.book.audiobook.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.tawhid.readout.book.audiobook.data.dto.AudioBookTrackResponseDto
import org.tawhid.readout.book.audiobook.data.dto.SearchResponseDto
import org.tawhid.readout.core.data.network.safeCall
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.utils.LIBRI_VOX_BASE_URL

class RemoteAudioBookDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteAudioBookDataSource {

    override suspend fun searchAudioBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("title", "^${query}")
                parameter("coverart", 1)
                parameter("limit", resultLimit)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchBrowseAudioBooks(
        genre: String?,
        offset: Int?,
        limit: Int
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiobooks"
            ) {
                parameter("coverart", 1)
                parameter("offset", offset)
                parameter("limit", limit)
                parameter("genre", genre)
                parameter("format", "json")
            }
        }
    }

    override suspend fun fetchAudioBookTracks(
        audioBookId: String
    ): Result<AudioBookTrackResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${LIBRI_VOX_BASE_URL}/audiotracks"
            ) {
                parameter("project_id", audioBookId)
                parameter("format", "json")
            }
        }
    }
}