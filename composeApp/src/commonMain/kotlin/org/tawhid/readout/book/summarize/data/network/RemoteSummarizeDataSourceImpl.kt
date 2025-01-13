package org.tawhid.readout.book.summarize.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tawhid.readout.BuildKonfig
import org.tawhid.readout.core.data.network.safeCall
import org.tawhid.readout.core.utils.DataError
import org.tawhid.readout.core.utils.Result
import org.tawhid.readout.core.gemini.dto.ContentItem
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto
import org.tawhid.readout.core.gemini.dto.RequestBody
import org.tawhid.readout.core.gemini.dto.RequestPart
import org.tawhid.readout.core.utils.GEMINI_BASE_URL
import org.tawhid.readout.core.utils.GEMINI_FLASH

class RemoteSummarizeDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteSummarizeDataSource {
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
                urlString = "$GEMINI_BASE_URL/v1beta/models/$GEMINI_FLASH:generateContent"
            ) {
                parameter("key", BuildKonfig.GEMINI_API_KEY)
                setBody(
                    Json.encodeToString(requestBody)
                )
            }
        }
    }
}