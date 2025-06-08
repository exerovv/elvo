package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.popular.PopularItemDTO
import com.example.elvo.domain.model.popular.PopularItem

fun List<PopularItemDTO>.toDomain(): List<PopularItem>{
    return this.map{
        it.toDomain()
    }
}

private fun PopularItemDTO.toDomain(): PopularItem {
    return PopularItem(
        title = this.title,
        url = this.url
    )
}