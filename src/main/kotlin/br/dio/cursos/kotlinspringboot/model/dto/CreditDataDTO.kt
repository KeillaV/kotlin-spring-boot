package br.dio.cursos.kotlinspringboot.model.dto

import java.math.BigDecimal
import java.time.LocalDate


data class CreditDataDTO(
    val creditValue: BigDecimal,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallments: Int = 0,
    var customerId: Long,
)
