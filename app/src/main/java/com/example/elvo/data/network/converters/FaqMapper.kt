package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.faq.FaqDTO
import com.example.elvo.domain.model.faq.Faq

private fun FaqDTO.toDomain(): Faq{
    return Faq(
        question = this.question,
        answer = this.answer
    )
}

fun List<FaqDTO>.toDomain(): List<Faq>{
    return this.map{
        it.toDomain()
    }
}