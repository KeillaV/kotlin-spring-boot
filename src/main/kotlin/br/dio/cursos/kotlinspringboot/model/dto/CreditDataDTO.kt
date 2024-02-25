package br.dio.cursos.kotlinspringboot.model.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate


data class CreditDataDTO(
    @field:NotNull(message = "Credit must have a valid value!") val creditValue: BigDecimal,
    @field:Future(message = "Date of first installment must be in the future!") val dayFirstInstallment: LocalDate,
    @field:Positive(message = "Invalid number of installments!") val numberOfInstallments: Int = 0,
    @field:Positive(message = "Invalid customer id!") var customerId: Long,
)
