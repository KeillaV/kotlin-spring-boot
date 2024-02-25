package br.dio.cursos.kotlinspringboot.model.dto

import br.dio.cursos.kotlinspringboot.model.enums.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreditViewDTO (
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val dayFirstInstallment: LocalDate,
    val numberOfInstallments: Int,
    val status: Status,
    val emailCustomer: String,
    val incomeCustomer: BigDecimal
)