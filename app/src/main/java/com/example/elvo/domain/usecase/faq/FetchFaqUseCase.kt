package com.example.elvo.domain.usecase.faq

import com.example.elvo.domain.model.faq.FaqResult
import com.example.elvo.domain.repositories.FaqRepository
import javax.inject.Inject

class FetchFaqUseCase @Inject constructor(
    private val faqRepository: FaqRepository
) {
    suspend operator fun invoke(): FaqResult = faqRepository.fetchFaq()
}