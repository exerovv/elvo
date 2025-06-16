package com.example.elvo.data.network.converters

import com.example.elvo.data.network.models.recipient.RecipientFullDTO
import com.example.elvo.data.network.models.recipient.RecipientShortDTO
import com.example.elvo.domain.model.recipient.RecipientFull
import com.example.elvo.domain.model.recipient.RecipientShort

private fun RecipientShortDTO.toDomain(): RecipientShort{
    return RecipientShort(
        this.recipientId,
        this.name
    )
}

fun RecipientFullDTO.toDomain(): RecipientFull{
    return RecipientFull(
        this.fullName,
        this.phone,
        this.address,
        this.name,
        this.surname,
        this.patronymic,
        this.city,
        this.street,
        this.house,
        this.building,
        this.flat,
        this.floor
    )
}

fun List<RecipientShortDTO>.toDomain() = this.map{
    it.toDomain()
}