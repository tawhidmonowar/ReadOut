package org.tawhid.readout.book.openbook.domain.usecase

import org.tawhid.readout.book.openbook.domain.repository.BookRepository

class DeleteBookFromSavedUseCase (
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(bookId: String) {
        bookRepository.deleteFromSaved(bookId)
    }
}