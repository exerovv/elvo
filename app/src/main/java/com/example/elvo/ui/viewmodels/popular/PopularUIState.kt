package com.example.elvo.ui.viewmodels.popular

import com.example.elvo.domain.model.popular.PopularItem

sealed class PopularUIState {
    data class Success(val data: List<PopularItem>): PopularUIState()
    data class Error(val errorResId: Int): PopularUIState()
    data object Default: PopularUIState()
}