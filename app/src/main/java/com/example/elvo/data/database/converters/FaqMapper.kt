package com.example.elvo.data.database.converters

import com.example.elvo.data.database.models.FaqEntity
import com.example.elvo.data.network.converters.toDomain
import com.example.elvo.data.network.models.faq.FaqDTO
import com.example.elvo.domain.model.faq.Faq

fun FaqEntity.toDomain() = Faq(question, answer)
fun FaqDTO.toEntity() = FaqEntity(question = question, answer = answer)
fun List<FaqDTO>.toEntity() = this.map{ it.toEntity() }
fun List<FaqEntity>.toDomain() = this.map{ it.toDomain() }
