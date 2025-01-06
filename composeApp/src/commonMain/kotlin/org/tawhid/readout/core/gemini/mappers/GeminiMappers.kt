package org.tawhid.readout.core.gemini.mappers

import org.tawhid.readout.core.gemini.domain.Gemini
import org.tawhid.readout.core.gemini.dto.CandidateDto
import org.tawhid.readout.core.gemini.dto.ContentDto
import org.tawhid.readout.core.gemini.dto.GeminiResponseDto
import org.tawhid.readout.core.gemini.dto.PartDto


fun GeminiResponseDto.toGemini(): Gemini {
    return Gemini(candidates.map { it.toCandidate() })
}

fun CandidateDto.toCandidate(): Gemini.Candidate {
    return Gemini.Candidate(content.toContent())
}

fun ContentDto.toContent(): Gemini.Content {
    return Gemini.Content(parts.map { it.toPart() }, role)
}

fun PartDto.toPart(): Gemini.Part {
    return Gemini.Part(text)
}