package br.dio.cursos.kotlinspringboot.model.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate


data class CreditDataDTO(
    @field:NotNull(message = "Credit must have a valid value!") val creditValue: BigDecimal,
    @field:Future(message = "Date of first installment must be in the future!") val dayFirstInstallment: LocalDate,
    @field:Min(value = 1, message = "Invalid number of installments! It's mandatory at least 1 installment") @field:Max(value = 48, message = "The maximum number of installments allowed is 48!") val numberOfInstallments: Int = 0,
    @field:Positive(message = "Invalid customer id!") var customerId: Long,
)
