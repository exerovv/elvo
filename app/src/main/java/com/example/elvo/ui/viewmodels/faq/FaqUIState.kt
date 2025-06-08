package com.example.elvo.ui.viewmodels.faq

import com.example.elvo.domain.model.faq.Faq

sealed class FaqUIState {
    data class Success(val data: List<Faq>): FaqUIState()
    data class Error(val errorResId: Int): FaqUIState()
    data object Default: FaqUIState()
}