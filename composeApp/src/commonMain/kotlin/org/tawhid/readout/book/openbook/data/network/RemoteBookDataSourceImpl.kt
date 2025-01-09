package org.tawhid.readout.book.openbook.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tawhid.readout.BuildKonfig
import org.tawhid.readout.book.openbook.data.dto.BookWorkDto
import org.tawhid.readout.book.openbook.data.dto.BrowseResponseDto
import org.tawhid.readout.book.openbook.data.dto.SearchResponseDto
import org.tawhid.readout.core.cloudtts.dto.AudioConfig
import org.tawhid.readout.core.cloudtts.dto.CloudTextToSpeechRequestDto
import org.tawhid.readout.core.cloudtts.dto.CloudTextToSpeechResponseDto
import org.tawhid.readout.core.cloudtts.dto.Input
import org.tawhid.readout.core.cloudtts.dto.Voice
import org.tawhid.readout.core.data.network.safeCall
import org.tawhid.readout.core.domain.DataError
import org.tawhid.readout.core.domain.Result
import org.tawhid.readout.core.gemini.dto.ContentItem
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto
import org.tawhid.readout.core.gemini.dto.RequestBody
import org.tawhid.readout.core.gemini.dto.RequestPart
import org.tawhid.readout.core.utils.CLOUD_TEXT_TO_SPEECH_BASE_URL
import org.tawhid.readout.core.utils.GEMINI_BASE_URL
import org.tawhid.readout.core.utils.GEMINI_FLASH
import org.tawhid.readout.core.utils.OPEN_LIBRARY_BASE_URL
import org.tawhid.readout.core.utils.USER_AGENT

class RemoteBookDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/search.json"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }


    override suspend fun fetchBookSummary(prompt: String): Result<GeminiResponseDto, DataError.Remote> {
        return safeCall<GeminiResponseDto> {
            val requestBody = RequestBody(
                contents = listOf(
                    ContentItem(
                        parts = listOf(RequestPart(text = prompt))
                    )
                )
            )
            httpClient.post(
                urlString = "${GEMINI_BASE_URL}/v1beta/models/${GEMINI_FLASH}:generateContent"
            ) {
                parameter("key", BuildKonfig.GEMINI_API_KEY)
                setBody(
                    Json.encodeToString(requestBody)
                )
            }
        }
    }

    override suspend fun fetchBookSummaryAudio(summary: String): Result<CloudTextToSpeechResponseDto, DataError.Remote> {
        return safeCall<CloudTextToSpeechResponseDto> {

            val requestBody = CloudTextToSpeechRequestDto(
                input = Input(
                    text = "Convert"
                ),
                audioConfig = AudioConfig(
                    audioEncoding = "MP3",
                    pitch = 0,
                    speakingRate = 1,
                ),
                voice = Voice(
                    languageCode = "en-US",
                    name = "en-US-Studio-Q"
                )
            )

            httpClient.post(
                urlString = "${CLOUD_TEXT_TO_SPEECH_BASE_URL}/v1beta1/text:synthesize"
            ) {
                header("Accept", "application/json")
                header("Content-Type", "application/json")
                parameter("key", BuildKonfig.CLOUD_TEXT_TO_SPEECH_API_KEY)
                setBody(
                    Json.encodeToString(requestBody)
                )
            }
        }
    }


    override suspend fun fetchBookDescription(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/works/$bookWorkId.json"
            ) {
                header("User-Agent", USER_AGENT)
            }
        }
    }

    override suspend fun fetchTrendingBooks(resultLimit: Int?): Result<BrowseResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/trending/daily.json"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("limit", resultLimit)
            }
        }
    }

    override suspend fun fetchBrowseBooks(
        subject: String,
        resultLimit: Int?,
        offset: Int?
    ): Result<BrowseResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "${OPEN_LIBRARY_BASE_URL}/subjects/$subject.json"
            ) {
                header("User-Agent", USER_AGENT)
                parameter("offset", offset)
                parameter("limit", resultLimit)
            }
        }
    }
}