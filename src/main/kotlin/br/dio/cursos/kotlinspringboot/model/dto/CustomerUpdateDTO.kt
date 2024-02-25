package br.dio.cursos.kotlinspringboot.model.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CustomerUpdateDTO (
    @field:NotEmpty(message = "Customer must have first name!") val firstName: String,
    @field:NotEmpty(message = "Customer must have last name!") val lastName: String,
    @field:NotNull(message = "Customer must have income!") val income: BigDecimal,
    @field:NotEmpty(message = "Customer must have address zip code!") val zipCode: String,
    @field:NotEmpty(message = "Customer must have address street!") val street: String
)