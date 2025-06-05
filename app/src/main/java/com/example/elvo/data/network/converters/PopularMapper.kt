package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.popular.PopularItemsDTO
import com.example.elvo.domain.model.popular.PopularItem

fun List<PopularItemsDTO>.toDomain(): List<PopularItem>{
    return this.map{
        it.toDomain()
    }
}

fun PopularItemsDTO.toDomain(): PopularItem {
    return PopularItem(
        title = this.title,
        url = this.url
    )
}